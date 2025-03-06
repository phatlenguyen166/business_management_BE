package vn.bookstore.app.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqSignInDTO;
import vn.bookstore.app.dto.response.TokenResponse;
import vn.bookstore.app.model.Token;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.util.error.InvalidDataException;

import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static vn.bookstore.app.util.constant.TokenType.ACCESS_TOKEN;
import static vn.bookstore.app.util.constant.TokenType.REFRESH_TOKEN;


public interface AuthenticationService {
    
    TokenResponse authenticate(ReqSignInDTO reqSignInDTO);
    
    TokenResponse refresh(HttpServletRequest request);
    
    String logout(HttpServletRequest request);
}

