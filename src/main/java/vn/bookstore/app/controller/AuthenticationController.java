package vn.bookstore.app.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.bookstore.app.dto.request.ReqChangePasswordDTO;
import vn.bookstore.app.dto.request.ReqSignInDTO;
import vn.bookstore.app.dto.response.ResTokenDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.AuthenticationService;

@RestController
@RequestMapping("api/v1/auth")
@Validated
@Slf4j
@Tag(name = "Authentication", description = "API xác thực người dùng")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/access")
    @Operation(summary = "Đăng nhập", description = "Người dùng đăng nhập và nhận access token cùng refresh token.")
    public ResponseEntity<ResponseDTO<ResTokenDTO>> login(@Valid @RequestBody ReqSignInDTO request) {
        return ResponseEntity.ok(ResponseDTO.success(true, "Đăng nhập thành công",
                authenticationService.authenticate(request)));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Làm mới token", description = "Dùng refresh token để lấy access token mới.")
    public ResponseEntity<ResponseDTO<ResTokenDTO>> refresh(HttpServletRequest request) {
        return ResponseEntity.ok(ResponseDTO.success(true, "Làm mới token thành công",
                authenticationService.refresh(request)));
    }

    @PostMapping("/logout")
    @Operation(summary = "Đăng xuất", description = "Xóa token của người dùng khỏi hệ thống.")
    public ResponseEntity<ResponseDTO<String>> logout(HttpServletRequest request) {
        return ResponseEntity.ok(ResponseDTO.success(true, authenticationService.logout(request), null));
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Quên mật khẩu", description = "Người dùng quên mật khẩu.")
    public ResponseEntity<ResponseDTO<String>> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        return ResponseEntity.ok(ResponseDTO.success(true, authenticationService.forgotPassword(email), null));
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Đổi mật khẩu", description = "Người dùng đổi mật khẩu.")
    public ResponseEntity<ResponseDTO<String>> changePassword(@RequestBody ReqChangePasswordDTO reqChangePasswordDTO) {
        return ResponseEntity.ok(ResponseDTO.success(true, authenticationService.resetPassword(reqChangePasswordDTO), null));
    }
}
