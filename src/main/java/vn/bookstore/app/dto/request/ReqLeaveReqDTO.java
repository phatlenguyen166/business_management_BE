package vn.bookstore.app.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.util.constant.LeaveTypeEnum;

import java.time.LocalDate;

@Getter
@Setter
public class ReqLeaveReqDTO {

    @NotBlank(message = "Title không được để trống")
    @Size(max = 255, message = "Title không được quá 255 ký tự")
    private String title;
    @NotNull(message = "Start date không được để trống")
    @Future(message = "Start date phải là ngày tương lai")
    private LocalDate startDate;
    @NotNull(message = "End date không được để trống")
    @Future(message = "End date phải là ngày trong tương lai")
    private LocalDate endDate;
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    @NotNull(message = "leaveReason không hợp lệ")
    private LeaveTypeEnum leaveReason;
    @NotNull(message = "userId không được để trống" )
    private Long userId;
}
