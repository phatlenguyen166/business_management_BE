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
import vn.bookstore.app.mapper.TokenMapper;
import vn.bookstore.app.model.*;
import vn.bookstore.app.repository.ContractRepository;
import vn.bookstore.app.repository.RoleRepository;
import vn.bookstore.app.repository.SeniorityLevelRepository;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.AuthenticationService;
import vn.bookstore.app.service.JwtService;
import vn.bookstore.app.service.TokenService;
import vn.bookstore.app.service.UserService;
import vn.bookstore.app.util.error.InvalidDataException;
import vn.bookstore.app.util.error.ResourceNotFoundException;

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
    private final RoleRepository roleRepository;
    
    public ResTokenDTO authenticate(ReqSignInDTO reqSignInDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                reqSignInDTO.getUsername(), reqSignInDTO.getPassword()));
        
        User user = userRepository.findByUsernameAndStatus(reqSignInDTO.getUsername(), 1)
                .orElseThrow(() -> new UsernameNotFoundException("Tên đăng nhập hoặc mật khẩu không chính xác"));
        
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
        
        User user = userRepository.findByUsernameAndStatus(userName, 1)
                .orElseThrow(() -> new UsernameNotFoundException("Tên đăng nhập hoặc mật khẩu không chính xác"));
        
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
        Role role = roleRepository.findRoleByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy role với Id:" + user.getId()));
        
        return TokenMapper.INSTANCE.mapToResTokenDTO(user, accessToken, refreshToken, role);
    }
}
