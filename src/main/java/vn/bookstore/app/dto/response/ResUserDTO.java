package vn.bookstore.app.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.util.constant.GenderEnum;

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
    private int status;
    private ResContractDTO resContractDTO;

}
