package vn.bookstore.app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.ReqUserDTO;
import vn.bookstore.app.dto.request.ReqUserWithContractDTO;
import vn.bookstore.app.dto.response.ResUserDTO;
import vn.bookstore.app.dto.response.ResResponse;
import vn.bookstore.app.service.impl.UserServiceImpl;
import vn.bookstore.app.util.error.ExistingIdException;
import vn.bookstore.app.util.error.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Users")
public class UserController {
    private final UserServiceImpl userService;
    
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }
    
    
    @GetMapping("/users")
    public ResponseEntity<ResResponse<List<ResUserDTO>>> fetchAllUsers() {
        List<ResUserDTO> resUserDTOList = this.userService.handleFetchAllUser();
        return ResponseEntity.ok(
                new ResResponse<>(
                        200,
                        null,
                        "Fetch all users successfully",
                        resUserDTOList
                )
        );
    }
    
    @PostMapping("/users")
    public ResponseEntity<ResResponse<ResUserDTO>> createUser(@Valid @RequestBody ReqUserWithContractDTO reqUser) throws ExistingIdException {
        if (userService.isExistUsername(reqUser.getUsername())) {
            throw new ExistingIdException("Tài khoản đã tồn tại trong hệ thống");
        }
        ResUserDTO newUser = this.userService.handleCreateUser(reqUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResResponse<>(
                        201,
                        null,
                        "Create user successfully",
                        newUser
                )
        );
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<ResResponse<ResUserDTO>> fetchUserById(@PathVariable Long id) throws NotFoundException {
        ResUserDTO user = this.userService.handleFetchUserById(id);
        if (user == null || !this.userService.isActive(id)) {
            throw new NotFoundException("Người dùng không tồn tại");
        }
        return ResponseEntity.ok().body(
                new ResResponse<>(
                        200,
                        null,
                        "Fetch user successfully",
                        user
                )
        );
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<ResResponse<ResUserDTO>> updateUser(@Valid @RequestBody ReqUserDTO updateUser,
                                                              @PathVariable Long id) throws NotFoundException {
        if (this.userService.handleFetchUserById(id) == null || !this.userService.isActive(id)) {
            throw new NotFoundException("Người dùng không tồn tại trong hệ thống");
        }
        ResUserDTO updatedUser = this.userService.handleUpdateUser(updateUser, id);
        return ResponseEntity.ok().body(
                new ResResponse<>(
                        200,
                        null,
                        "Update user successfully",
                        updatedUser
                )
        );
    }
    
    @PatchMapping("/users/{id}")
    public ResponseEntity<ResResponse> deleteUserById(@PathVariable Long id) throws NotFoundException {
        if (this.userService.handleFetchUserById(id) == null || !this.userService.isActive(id)) {
            throw new NotFoundException("Người dùng không tồn tại trong hệ thống");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok().body(
                new ResResponse<>(
                        200,
                        null,
                        "Delete user successfully",
                        null
                )
        );
    }
    
    
}
