package vn.bookstore.app.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
public class ResSupplierDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private int status;
    private double percentage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    
}
