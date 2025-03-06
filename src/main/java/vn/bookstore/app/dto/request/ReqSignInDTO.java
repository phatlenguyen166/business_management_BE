package vn.bookstore.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import vn.bookstore.app.util.constant.Platform;

import java.io.Serializable;

@Getter
public class ReqSignInDTO implements Serializable {
    
    @NotBlank(message = "username must be not null")
    private String username;
    
    @NotBlank(message = "password must be not null")
    private String password;
    
    @NotNull(message = "username must be not null")
    private Platform platform;
    
}
