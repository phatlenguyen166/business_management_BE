package vn.bookstore.app.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.ReqUserDTO;
import vn.bookstore.app.dto.request.ReqUserWithContractDTO;
import vn.bookstore.app.dto.response.ResUserDTO;
import vn.bookstore.app.dto.response.RestResponse;
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
    public ResponseEntity<RestResponse<List<ResUserDTO>>> fetchAllUsers() {
        List<ResUserDTO> resUserDTOList = this.userService.handleFetchAllUser();
        return ResponseEntity.ok(
                new RestResponse<>(
                        200,
                        null,
                        "Fetch all users successfully",
                        resUserDTOList
                )
        );
    }

    @PostMapping("/users")
    public ResponseEntity<RestResponse<ResUserDTO>> createUser(@Valid @RequestBody ReqUserWithContractDTO reqUser) throws IdInvalidException {
        if (userService.isExistUsername(reqUser.getUsername())) {
            throw new IdInvalidException("Tài khoản đã tồn tại trong hệ thống");
        }
       ResUserDTO newUser =  this.userService.handleCreateUser(reqUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new RestResponse<>(
                        201,
                        null,
                        "Create user successfully",
                        newUser
                )
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<RestResponse<ResUserDTO>> fetchUserById(@PathVariable Long id) throws IdInvalidException {
        ResUserDTO user = this.userService.handleFetchUserById(id);
        if (user == null || !this.userService.isActive(id)) {
            throw new IdInvalidException("Người dùng không tồn tại");
        }
        return ResponseEntity.ok().body(
                new RestResponse<>(
                        200,
                        null,
                        "Fetch user successfully",
                        user
                )
        );
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<RestResponse<ResUserDTO>> updateUser(@Valid @RequestBody ReqUserDTO updateUser, @PathVariable Long id) throws IdInvalidException {
        if (this.userService.handleFetchUserById(id) == null || !this.userService.isActive(id)) {
            throw new IdInvalidException("Người dùng không tồn tại trong hệ thống");
        }
        ResUserDTO updatedUser = this.userService.handleUpdateUser(updateUser,id);
        return ResponseEntity.ok().body(
                new RestResponse<>(
                        200,
                        null,
                        "Update user successfully",
                        updatedUser
                )
        );
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<RestResponse> deleteUserById(@PathVariable Long id) throws IdInvalidException {
        if (this.userService.handleFetchUserById(id) == null || !this.userService.isActive(id)) {
            throw new IdInvalidException("Người dùng không tồn tại trong hệ thống");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok().body(
                new RestResponse<>(
                        200,
                        null,
                        "Delete user successfully",
                        null
                )
        );
    }


}
