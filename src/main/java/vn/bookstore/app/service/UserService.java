package vn.bookstore.app.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.bookstore.app.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDetailsService userDetailsService();
    
    public List<User> handleFetchAllUser();
    
    public User handleCreateUser(User user);
    
    public User handleFetchUserById(Long id);
    
    public User handleUpdateUser(User user);
    
    public void handleDeleteUser(Long id);
    
    public boolean isExistUsername(String username);
}

