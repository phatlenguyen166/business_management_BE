package vn.bookstore.app.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqSignInDTO;
import vn.bookstore.app.dto.response.ResTokenDTO;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.Role;
import vn.bookstore.app.model.Token;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.ContractRepository;
import vn.bookstore.app.repository.RoleRepository;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.AuthenticationService;
import vn.bookstore.app.service.JwtService;
import vn.bookstore.app.service.TokenService;
import vn.bookstore.app.util.error.InvalidDataException;

import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static vn.bookstore.app.util.constant.TokenType.ACCESS_TOKEN;
import static vn.bookstore.app.util.constant.TokenType.REFRESH_TOKEN;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ContractRepository contractRepository;
    private final RoleRepository roleRepository;
    
    public ResTokenDTO authenticate(ReqSignInDTO reqSignInDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqSignInDTO.getUsername(),
                reqSignInDTO.getPassword()));
        
        User user = userRepository.findByUsernameAndStatus(reqSignInDTO.getUsername(), 1)
                .orElseThrow(() -> new UsernameNotFoundException("Tên đăng nhập hoặc mật khẩu không chính xác"));
        
        Contract roleIdInContract = contractRepository.findContractByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy hợp đồng của người dùng"));
        
        Role role = roleRepository.findRoleByIdAndStatus(roleIdInContract.getId(), 1)
                .orElseThrow(() -> new IllegalStateException("Không ttìm thấy chức vụ"));
        
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        
        tokenService.save(
                Token.builder()
                        .username(user.getUsername())
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build()
        );
        
        // 7. Trả về DTO chứa thông tin
        return ResTokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .roleInfo(role)
                .build();
    }
    
    public ResTokenDTO refresh(HttpServletRequest request) {
        String refreshToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(refreshToken)) {
            throw new InvalidDataException("Token must be not blank");
        }
        
        final String userName = jwtService.extractUsername(refreshToken, REFRESH_TOKEN);
        
        System.out.println("userName = " + userName);
        
        Optional<User> user = userRepository.findByUsernameAndStatus(userName, 1);
        
        if (!jwtService.isValid(refreshToken, REFRESH_TOKEN, user.get())) {
            throw new InvalidDataException("Token is invalid");
        }
        
        String accessToken = jwtService.generateToken(user.get());
        
        return ResTokenDTO.builder().accessToken(accessToken).refreshToken(refreshToken).userId(user.get().getId()).build();
        
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
}
