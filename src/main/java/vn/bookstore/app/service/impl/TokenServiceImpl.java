package vn.bookstore.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.bookstore.app.model.Token;
import vn.bookstore.app.repository.TokenRepository;
import vn.bookstore.app.service.TokenService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    
    private final TokenRepository tokenRepository;
    
    public long save(Token token) {
        Optional<Token> optional = tokenRepository.findByUsername(token.getUsername());
        
        if (optional.isEmpty()) {
            tokenRepository.save(token);
            return token.getId();
        } else {
            Token currentToken = optional.get();
            currentToken.setAccessToken(token.getAccessToken());
            currentToken.setRefreshToken(token.getRefreshToken());
            tokenRepository.save(currentToken);
            return currentToken.getId();
        }
    }
    
    public void delete(String username) {
        Token token = getByUsername(username);
        tokenRepository.delete(token);
    }
    
    public Token getByUsername(String username) {
        return tokenRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not" +
                " found"));
    }
}
