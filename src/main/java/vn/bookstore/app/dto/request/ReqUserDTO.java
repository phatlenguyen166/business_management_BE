package vn.bookstore.app.dto.request;

import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.util.constant.GenderEnum;

import java.time.Instant;
import java.time.LocalDate;
@Getter
@Setter
public class ReqUserDTO {
    private String fullName;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String address;
    private GenderEnum gender;
    private String username;
    private String password;
}
