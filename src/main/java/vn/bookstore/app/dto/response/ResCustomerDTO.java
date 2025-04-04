package vn.bookstore.app.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResCustomerDTO {

    private Long id;
    private String idString;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
