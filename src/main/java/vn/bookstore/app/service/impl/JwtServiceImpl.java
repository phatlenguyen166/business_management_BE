package vn.bookstore.app.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.bookstore.app.service.JwtService;
import vn.bookstore.app.util.constant.TokenType;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static vn.bookstore.app.util.constant.TokenType.ACCESS_TOKEN;
import static vn.bookstore.app.util.constant.TokenType.REFRESH_TOKEN;


@Service
public class JwtServiceImpl implements JwtService {
    
    @Value("${jwt.expiryHour}")
    private long expiryHour;
    
    @Value("${jwt.expiryDay}")
    private long expiryDay;
    
    @Value("${jwt.secretKey}")
    private String secretKey;
    
    @Value("${jwt.refreshKey}")
    private String refreshKey;
    
    @Override
    public String generateToken(UserDetails user) {
        return generateToken(new HashMap<>(), user);
    }
    
    @Override
    public String generateRefreshToken(UserDetails user) {
        return generateRefreshToken(new HashMap<>(), user);
    }
    
    @Override
    public String extractUsername(String token, TokenType type) {
        return extractClaim(token, type, Claims::getSubject);
        
    }
    
    @Override
    public boolean isValid(String token, TokenType type, UserDetails userDetails) {
        final String username = extractUsername(token, type);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, type));
    }
    
    
    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiryHour)).signWith(getKey(ACCESS_TOKEN),
                        SignatureAlgorithm.HS256).compact();
    }
    
    private String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * expiryDay)).signWith(getKey(REFRESH_TOKEN), SignatureAlgorithm.HS256).compact();
    }
    
    private Key getKey(TokenType type) {
        byte[] keyBytes;
        if (ACCESS_TOKEN.equals(type)) {
            keyBytes = Decoders.BASE64.decode(secretKey);
        } else {
            keyBytes = Decoders.BASE64.decode(refreshKey);
        }
        
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    private <T> T extractClaim(String token, TokenType type, Function<Claims, T> claimResolver) {
        final Claims claims = extraAllClaims(token, type);
        return claimResolver.apply(claims);
    }
    
    private Claims extraAllClaims(String token, TokenType type) {
        return Jwts.parser().setSigningKey(getKey(type)).build().parseClaimsJws(token).getBody();
    }
    
    private boolean isTokenExpired(String token, TokenType type) {
        return extractExpiration(token, type).before(new Date());
    }
    
    private Date extractExpiration(String token, TokenType type) {
        return extractClaim(token, type, Claims::getExpiration);
    }
}
