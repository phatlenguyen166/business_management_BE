package vn.bookstore.app.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.util.constant.LeaveTypeEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ResLeaveReqDTO {
    private Long id;
    private String idString;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate sendDate;
    private String description;
    @Enumerated(EnumType.STRING)
    private LeaveTypeEnum leaveReason;
    private LocalDateTime updatedAt;
    private int status;
    private Long userId;
}
