package vn.bookstore.app.service.impl;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.bookstore.app.config.AppConfig;
import vn.bookstore.app.controller.UserController;
import vn.bookstore.app.dto.request.ReqUserDTO;
import vn.bookstore.app.dto.response.ResUserDTO;
import vn.bookstore.app.mapper.UserConverter;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.UserService;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserConverter userConverter;
    private PasswordEncoder passwordEncoder;
    
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsernameAndStatus(username, 1).orElseThrow(() -> new UsernameNotFoundException(
                "Username not found or user is not active"));
    }
    
    public List<ResUserDTO> handleFetchAllUser() {
        List<ResUserDTO> resUserDTOS = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            ResUserDTO resUserDTO = this.userConverter.convertToResUserDTO(user);
            resUserDTO.setIdString("NV-" + user.getId());
            resUserDTOS.add(resUserDTO);
        }
        return resUserDTOS;
    }
    
    
    public ResUserDTO handleCreateUser(ReqUserDTO reqUser) {
        String hashPassWord = this.passwordEncoder.encode(reqUser.getPassword());
        reqUser.setPassword(hashPassWord);
        User newUser = this.userConverter.convertToUser(reqUser);
        newUser.setStatus(1);
        this.userRepository.save(newUser);
        return this.userConverter.convertToResUserDTO(newUser);
    }
    
    public ResUserDTO handleFetchUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return this.userConverter.convertToResUserDTO(user.get());
        }
        return null;
    }
    
    public ResUserDTO handleUpdateUser(ReqUserDTO updateUser, Long id) {
        Optional<User> currentUser = this.userRepository.findById(id);
        String hashPassWord = this.passwordEncoder.encode(updateUser.getPassword());
        if (currentUser.isPresent()) {
            currentUser.get().setFullName(updateUser.getFullName());
            currentUser.get().setAddress(updateUser.getAddress());
            currentUser.get().setDateOfBirth(updateUser.getDateOfBirth());
            currentUser.get().setEmail(updateUser.getEmail());
            currentUser.get().setGender(updateUser.getGender());
            currentUser.get().setPhoneNumber(updateUser.getPhoneNumber());
            currentUser.get().setPassword(hashPassWord);
            this.userRepository.save(currentUser.get());
            return this.userConverter.convertToResUserDTO(currentUser.get());
        }
        return null;
    }
    
    public void handleDeleteUser(Long id) {
        Optional<User> currentUser = this.userRepository.findById(id);
        if (currentUser.isPresent()) {
            currentUser.get().setStatus(0);
            this.userRepository.save(currentUser.get());
        }
    }
    
    public boolean isExistUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public boolean isActive(Long id) {
        Optional<User> currentUser = this.userRepository.findById(id);
        if (currentUser.isPresent()) {
            if (currentUser.get().getStatus() == 0) {
                return false;
            }
            return true;
        }
        return false;
    }
    
}
