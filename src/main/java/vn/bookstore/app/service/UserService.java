package vn.bookstore.app.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.bookstore.app.dto.request.ReqUserDTO;
import vn.bookstore.app.dto.request.ReqUserWithContractDTO;
import vn.bookstore.app.dto.response.ResUserDTO;
import vn.bookstore.app.model.User;


import java.util.List;
import java.util.Optional;

public interface    UserService {
    UserDetailsService userDetailsService();

    public List<ResUserDTO> handleFetchAllUser();

    public ResUserDTO handleCreateUser(ReqUserWithContractDTO user);

    public ResUserDTO handleFetchUserById(Long id);

    public ResUserDTO handleUpdateUser(ReqUserDTO updateUser, Long id);

    public void handleDeleteUser(Long id);

    public boolean isExistUsername(String username);

    public boolean isActive(Long id);

    Long saveUser(User user);

    User findUserByEmail(String email);

    User findByUsernameAndStatus(String username, int status);

    User findUserById(Long id);

    User findById(@NotNull(message = "Người dùng không được để trống") Long userId);
}

