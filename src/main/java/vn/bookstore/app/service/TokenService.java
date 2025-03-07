package vn.bookstore.app.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.bookstore.app.model.Token;
import vn.bookstore.app.repository.TokenRepository;

import java.util.Optional;

public interface TokenService {
    public long save(Token token);
    
    public void delete(String username);
    
    public Token getByUsername(String username);
}
