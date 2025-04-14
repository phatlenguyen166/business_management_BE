package vn.bookstore.app.service;

import org.springframework.security.core.userdetails.UserDetails;

import vn.bookstore.app.util.constant.TokenType;

public interface JwtService {

    String generateToken(UserDetails user);

    String generateRefreshToken(UserDetails user);

    String generateResetToken(UserDetails user);

    String extractUsername(String token, TokenType type);

    boolean isValid(String token, TokenType type, UserDetails userDetails);
}
