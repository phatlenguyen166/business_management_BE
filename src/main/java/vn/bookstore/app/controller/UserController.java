package vn.bookstore.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import vn.bookstore.app.dto.request.ReqUserDTO;
import vn.bookstore.app.dto.request.ReqUserWithContractDTO;
import vn.bookstore.app.dto.response.ResUserDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.impl.UserServiceImpl;
import vn.bookstore.app.util.error.ExistingIdException;
import vn.bookstore.app.util.error.NotFoundValidException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/users/me")
    @Operation(summary = "Lấy thông tin người dùng hiện tại", description = "API này trả về thông tin của người dùng hiện tại dựa trên token.")
    public ResponseEntity<ResponseDTO<ResUserDTO>> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        ResUserDTO currentUser = userService.handleFetchUserByUsername(username);
        return ResponseEntity
                .ok(new ResponseDTO<>(200, true, null, "Lấy thông tin người dùng thành công", currentUser));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseDTO<List<ResUserDTO>>> fetchAllUsers() {
        List<ResUserDTO> resUserDTOList = this.userService.handleFetchAllUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Fetch all users successfully",
                        resUserDTOList));
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseDTO<ResUserDTO>> createUser(@Valid @RequestBody ReqUserWithContractDTO reqUser)
            throws ExistingIdException {
        if (userService.isExistUsername(reqUser.getUsername())) {
            throw new ExistingIdException("Tài khoản đã tồn tại trong hệ thống");
        }
        ResUserDTO newUser = this.userService.handleCreateUser(reqUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO<>(
                        201,
                        true,
                        null,
                        "Create user successfully",
                        newUser));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseDTO<ResUserDTO>> fetchUserById(@PathVariable Long id) throws NotFoundValidException {
        ResUserDTO user = this.userService.handleFetchUserById(id);
        if (user == null || !this.userService.isActive(id)) {
            throw new NotFoundValidException("Người dùng không tồn tại");
        }
        return ResponseEntity.ok().body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Fetch user successfully",
                        user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseDTO<ResUserDTO>> updateUser(@Valid @RequestBody ReqUserDTO updateUser,
            @PathVariable Long id) throws NotFoundValidException {
        if (this.userService.handleFetchUserById(id) == null || !this.userService.isActive(id)) {
            throw new NotFoundValidException("Người dùng không tồn tại trong hệ thống");
        }
        ResUserDTO updatedUser = this.userService.handleUpdateUser(updateUser, id);
        return ResponseEntity.ok().body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Update user successfully",
                        updatedUser));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteUserById(@PathVariable Long id) throws NotFoundValidException {
        if (this.userService.handleFetchUserById(id) == null || !this.userService.isActive(id)) {
            throw new NotFoundValidException("Người dùng không tồn tại trong hệ thống");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok().body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Delete user successfully",
                        null));
    }

}
