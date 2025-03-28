package vn.bookstore.app.dto.response;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResTokenDTO implements Serializable {

    private String accessToken;

    private String refreshToken;

    private Long userId;

    private String roleName;
}
