package vn.bookstore.app.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.bookstore.app.util.constant.TokenType;

@Service
public interface JwtService {
    
    String generateToken(UserDetails user);
    
    String generateRefreshToken(UserDetails user);
    
    String extractUsername(String token, TokenType type);
    
    boolean isValid(String token, TokenType type, UserDetails userDetails);
    
    
}
