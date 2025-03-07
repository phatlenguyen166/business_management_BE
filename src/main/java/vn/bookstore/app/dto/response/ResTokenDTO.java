package vn.bookstore.app.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.model.Role;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ResTokenDTO implements Serializable {
    private String accessToken;
    
    private String refreshToken;
    
    private Long userId;
    
    private Role roleInfo;
}
