package vn.bookstore.app.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqChangePasswordDTO;
import vn.bookstore.app.dto.request.ReqSignInDTO;
import vn.bookstore.app.dto.response.ResTokenDTO;
import vn.bookstore.app.mapper.TokenMapper;
import vn.bookstore.app.model.*;
import vn.bookstore.app.service.*;
import vn.bookstore.app.util.error.InvalidDataException;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static vn.bookstore.app.util.constant.TokenType.*;

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

    public ResTokenDTO authenticate(ReqSignInDTO reqSignInDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                reqSignInDTO.getUsername(), reqSignInDTO.getPassword()));

        User user = userService.findByUsernameAndStatus(reqSignInDTO.getUsername(),1);

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


    public ResTokenDTO refresh(HttpServletRequest request) {
        String refreshToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(refreshToken)) {
            throw new InvalidDataException("Token must be not blank");
        }

        final String userName = jwtService.extractUsername(refreshToken, REFRESH_TOKEN);

        User user = userService.findByUsernameAndStatus(userName,1);

        if (!jwtService.isValid(refreshToken, REFRESH_TOKEN, user)) {
            throw new InvalidDataException("Token không hợp lệ");
        }

        String accessToken = jwtService.generateToken(user);

        return generateTokenResponse(user, accessToken, refreshToken);
    }


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
        String confirmLink = "https://localhost:5173/reset-password?token=" + resetToken;
        emailService.sendEmail(user.getEmail(), "Reset Password", "Click vào link để đặt lại mật khẩu:\n" + confirmLink);

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
        User user = userService.findByUsernameAndStatus(userName,1);
        if (!jwtService.isValid(secretKey, RESET_TOKEN, user)) {
            throw new InvalidDataException("Reset password fail");
        }
        return user;
    }
}
