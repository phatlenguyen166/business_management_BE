package vn.bookstore.app.service.impl;

import org.apache.commons.lang3.StringUtils;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.bookstore.app.dto.request.ReqChangePasswordDTO;
import vn.bookstore.app.dto.request.ReqSignInDTO;
import vn.bookstore.app.dto.response.ResTokenDTO;
import vn.bookstore.app.mapper.TokenMapper;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.Role;
import vn.bookstore.app.model.Token;
import vn.bookstore.app.model.User;
import vn.bookstore.app.service.AuthenticationService;
import vn.bookstore.app.service.ContractService;
import vn.bookstore.app.service.EmailService;
import vn.bookstore.app.service.JwtService;
import vn.bookstore.app.service.RoleService;
import vn.bookstore.app.service.TokenService;
import vn.bookstore.app.service.UserService;
import static vn.bookstore.app.util.constant.TokenType.ACCESS_TOKEN;
import static vn.bookstore.app.util.constant.TokenType.REFRESH_TOKEN;
import static vn.bookstore.app.util.constant.TokenType.RESET_TOKEN;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import vn.bookstore.app.util.error.InvalidDataException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RoleService roleService;
    private final ContractService contractService;

    @Override
    public ResTokenDTO authenticate(ReqSignInDTO reqSignInDTO) {
        // Xác thực thông tin đăng nhập
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                reqSignInDTO.getUsername(), reqSignInDTO.getPassword()));

        // Tìm thông tin người dùng
        User user = userService.findByUsernameAndStatus(reqSignInDTO.getUsername(), 1);

        // Tìm hợp đồng của người dùng
        Contract contract = contractService.findByUsername(reqSignInDTO.getUsername(), 1);
        log.error("-----------------------------------------------------------0---------------------------------------Hợp đồng của người dùng: " + contract.getStartDate());
        // Kiểm tra nếu người dùng là nhân viên (không phải ADMIN) và có hợp đồng
        if (contract != null && !user.getUsername().equals("ADMIN")) {
            // Lấy ngày hiện tại
            LocalDate today = LocalDate.now();

            // Kiểm tra nếu ngày bắt đầu làm việc trong hợp đồng sau ngày hiện tại
            if (contract.getStartDate().isAfter(today)) {
                throw new InvalidDataException("Tài khoản chưa được kích hoạt. Hợp đồng của bạn sẽ bắt đầu từ ngày "
                        + contract.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            
            }
        }

        // Tạo token
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Token token = Token.builder()
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        tokenService.save(token);

        return generateTokenResponse(user, accessToken, refreshToken);
    }

    @Override
    public ResTokenDTO refresh(HttpServletRequest request) {
        String refreshToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(refreshToken)) {
            throw new InvalidDataException("Token must be not blank");
        }

        final String userName = jwtService.extractUsername(refreshToken, REFRESH_TOKEN);

        User user = userService.findByUsernameAndStatus(userName, 1);

        if (!jwtService.isValid(refreshToken, REFRESH_TOKEN, user)) {
            throw new InvalidDataException("Token không hợp lệ");
        }

        String accessToken = jwtService.generateToken(user);

        return generateTokenResponse(user, accessToken, refreshToken);
    }

    @Override
    public String logout(HttpServletRequest request) {
        log.info("---------- logout ----------");

        final String token = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            throw new InvalidDataException("Token must be not blank");
        }

        final String userName = jwtService.extractUsername(token, ACCESS_TOKEN);
        tokenService.delete(userName);

        return "Deleted!";
    }

    public ResTokenDTO generateTokenResponse(User user, String accessToken, String refreshToken) {
        Role role = roleService.getRoleByUserId(user.getId());

        return TokenMapper.INSTANCE.mapToResTokenDTO(user, accessToken, refreshToken, role);
    }

    @Override
    public String forgotPassword(String email) {
        User user = userService.findUserByEmail(email);
        if (!user.isEnabled()) {
            throw new InvalidDataException("User không hoạt động");
        }

        String resetToken = jwtService.generateResetToken(user);
        String confirmLink = "http://localhost:5173/forgot-password?token=" + resetToken;
        emailService.sendEmail(user.getEmail(), "Reset Password", confirmLink);

        log.info("--------------> Forgot password reset link: {}", confirmLink);
        return "Password reset link has been sent to your email.";
    }

    @Override
    public String resetPassword(ReqChangePasswordDTO request) {
        User user = isValidUserByToken(request.getSecretKey());

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new InvalidDataException("Password mismatch");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.saveUser(user);

        return "Password changed successfully.";
    }

    private User isValidUserByToken(String secretKey) {
        final String userName = jwtService.extractUsername(secretKey, RESET_TOKEN);
        User user = userService.findByUsernameAndStatus(userName, 1);
        if (!jwtService.isValid(secretKey, RESET_TOKEN, user)) {
            throw new InvalidDataException("Reset password fail");
        }
        return user;
    }
}
