package vn.bookstore.app.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.bookstore.app.dto.request.ReqUserDTO;
import vn.bookstore.app.dto.request.ReqUserWithContractDTO;
import vn.bookstore.app.dto.response.ResUserDTO;


import java.util.List;

public interface    UserService {
    UserDetailsService userDetailsService();

    public List<ResUserDTO> handleFetchAllUser();

    public ResUserDTO handleCreateUser(ReqUserWithContractDTO user);

    public ResUserDTO handleFetchUserById(Long id);

    public ResUserDTO handleUpdateUser(ReqUserDTO updateUser, Long id);

    public void handleDeleteUser(Long id);

    public boolean isExistUsername(String username);

    public boolean isActive(Long id);




}

