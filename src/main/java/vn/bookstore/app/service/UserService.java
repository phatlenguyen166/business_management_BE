package vn.bookstore.app.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.bookstore.app.dto.request.ReqUserDTO;
import vn.bookstore.app.dto.response.ResUserDTO;
import vn.bookstore.app.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDetailsService userDetailsService();
    
    public List<ResUserDTO> handleFetchAllUser();
    
    public ResUserDTO handleCreateUser(ReqUserDTO user);
    
    public ResUserDTO handleFetchUserById(Long id);
    
    public ResUserDTO handleUpdateUser(ReqUserDTO updateUser, Long id);
    
    public void handleDeleteUser(Long id);
    
    public boolean isExistUsername(String username);

    public boolean isActive(Long id);
}

