package vn.bookstore.app.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReqAttendanceDetailDTO {
    @NotNull(message = "User ID không được để trống")
    private Long userId;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

}
