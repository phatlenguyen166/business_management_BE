package vn.bookstore.app.dto.response;

import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.util.constant.GenderEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ResUserDTO {
    private Long id;
    private String idString;
    private String fullName;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String address;
    private GenderEnum gender;
    private String username;
    private LocalDateTime createdAt;
    private Long roleId;
    private int status;
}
