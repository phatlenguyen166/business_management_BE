package vn.bookstore.app.service.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.UserService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                "Username not found"));
    }
    
    public List<User> handleFetchAllUser() {
        return userRepository.findAll();
    }
    
    
    public User handleCreateUser(User user) {
        user.setStatus(1);
        return this.userRepository.save(user);
    }
    
    public User handleFetchUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
    
    public User handleUpdateUser(User user) {
        User currentUser = handleFetchUserById(user.getId());
        if (user.getPassword() != null) currentUser.setPassword(user.getPassword());
        this.userRepository.save(currentUser);
        return currentUser;
    }
    
    public void handleDeleteUser(Long id) {
        User currentUser = handleFetchUserById(id);
        if (currentUser != null) {
            currentUser.setStatus(0);
            this.userRepository.save(currentUser);
        }
    }
    
    public boolean isExistUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
}
