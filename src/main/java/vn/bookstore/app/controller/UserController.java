package vn.bookstore.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.LoginDTO;
import vn.bookstore.app.dto.request.ReqUserDTO;
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
    public ResponseEntity<List<User>> fetchAllUsers() {
        return ResponseEntity.ok().body(this.userService.handleFetchAllUser());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User reqUser) throws IdInvalidException {
        if (userService.isExistUsername(reqUser.getUsername())) {
            throw new IdInvalidException("Tài khoản đã tồn tại trong hệ thống");
        }
       User newUser =  this.userService.handleCreateUser(reqUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> fetchUserById(@PathVariable Long id) throws IdInvalidException {
        User user = this.userService.handleFetchUserById(id);
        if (user == null) {
            throw new IdInvalidException("Người dùng không tồn tại");
        }
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User updateUser) throws IdInvalidException {
        if (this.userService.handleFetchUserById(updateUser.getId()) == null) {
            throw new IdInvalidException("Người dùng không tồn tại trong hệ thống");
        }
        return ResponseEntity.ok().body(this.userService.handleUpdateUser(updateUser));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok().body(null);
    }
}
