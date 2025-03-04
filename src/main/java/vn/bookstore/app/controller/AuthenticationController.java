package vn.bookstore.app.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.SignInRequest;
import vn.bookstore.app.dto.response.TokenResponse;
import vn.bookstore.app.service.AuthenticationService;
import vn.bookstore.app.service.UserService;

@RestController
@RequestMapping("/auth")
@Validated
@Slf4j
@Tag(name = "User Controller")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final UserService userService;
    
    private final AuthenticationService authenticationService;
    
    @PostMapping("/access")
    public ResponseEntity<TokenResponse> login(@RequestBody SignInRequest request) {
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
    }
    
    
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(HttpServletRequest request) {
        return new ResponseEntity<>(authenticationService.refresh(request), HttpStatus.OK);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return new ResponseEntity<>(authenticationService.logout(request), HttpStatus.OK);
    }
}
