package vn.bookstore.app.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.SignInRequest;
import vn.bookstore.app.dto.response.ResTokenDTO;
import vn.bookstore.app.model.Token;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.util.error.InvalidDataException;

import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static vn.bookstore.app.util.constant.TokenType.ACCESS_TOKEN;
import static vn.bookstore.app.util.constant.TokenType.REFRESH_TOKEN;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    
    
    public ResTokenDTO authenticate(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),
                signInRequest.getPassword()));
        
        var user =
                userRepository.findByUsernameAndStatus(signInRequest.getUsername(), 1).orElseThrow(() -> new UsernameNotFoundException("Username or password incorrect"));
        
        String accessToken = jwtService.generateToken(user);
        
        String refreshToken = jwtService.generateRefreshToken(user);
        
        tokenService.save(Token.builder().username(user.getUsername()).accessToken(accessToken).refreshToken(refreshToken).build());
        
        return ResTokenDTO.builder().accessToken(accessToken).refreshToken(refreshToken).userId(user.getId()).build();
    }
    
    public ResTokenDTO refresh(HttpServletRequest request) {
        String refreshToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(refreshToken)) {
            throw new InvalidDataException("Token must be not blank");
        }
        // extract user from token
        final String userName = jwtService.extractUsername(refreshToken, REFRESH_TOKEN);
        
        System.out.println("userName = " + userName);
        // check it into db
        Optional<User> user = userRepository.findByUsernameAndStatus(userName, 1);
        System.out.println("userID = " + user.get().getId());
        
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

