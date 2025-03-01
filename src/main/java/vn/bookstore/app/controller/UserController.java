package vn.bookstore.app.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.ReqUserDTO;
import vn.bookstore.app.dto.response.ResUserDTO;
import vn.bookstore.app.model.User;
import vn.bookstore.app.service.impl.UserServiceImpl;
import vn.bookstore.app.util.error.IdInvalidException;
import java.util.List;

@RestController
public class UserController {
    private UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResUserDTO>> fetchAllUsers() {
        return ResponseEntity.ok().body(this.userService.handleFetchAllUser());
    }

    @PostMapping("/users")
    public ResponseEntity<ResUserDTO> createUser(@Valid @RequestBody ReqUserDTO reqUser) throws IdInvalidException {
        if (userService.isExistUsername(reqUser.getUsername())) {
            throw new IdInvalidException("Tài khoản đã tồn tại trong hệ thống");
        }
       ResUserDTO newUser =  this.userService.handleCreateUser(reqUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResUserDTO> fetchUserById(@PathVariable Long id) throws IdInvalidException {
        ResUserDTO user = this.userService.handleFetchUserById(id);
        if (user == null || !this.userService.isActive(id)) {
            throw new IdInvalidException("Người dùng không tồn tại");
        }
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ResUserDTO> updateUser(@Valid @RequestBody ReqUserDTO updateUser, @PathVariable Long id) throws IdInvalidException {
        if (this.userService.handleFetchUserById(id) == null || !this.userService.isActive(id)) {
            throw new IdInvalidException("Người dùng không tồn tại trong hệ thống");
        }
        return ResponseEntity.ok().body(this.userService.handleUpdateUser(updateUser,id));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) throws IdInvalidException {
        if (this.userService.handleFetchUserById(id) == null || !this.userService.isActive(id)) {
            throw new IdInvalidException("Người dùng không tồn tại trong hệ thống");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok().body(null);
    }


}
