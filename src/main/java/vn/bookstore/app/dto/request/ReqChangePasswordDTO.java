package vn.bookstore.app.dto.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqChangePasswordDTO implements Serializable {
    private String secretKey;
    private String password;
    private String confirmPassword;

}
