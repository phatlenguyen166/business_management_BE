package vn.bookstore.app.dto.response;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.model.User;
import vn.bookstore.app.util.constant.AttendanceStatusEnum;
import vn.bookstore.app.util.constant.LeaveTypeEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class ResAttendanceDetailDTO {
    private Long id;
    private String idString;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private LocalDate workingDay;
    @Enumerated(EnumType.STRING)
    private AttendanceStatusEnum attendanceStatus;
    @Enumerated(EnumType.STRING)
    private LeaveTypeEnum leaveTypeEnum;
    private Long userId;
    private String userIdString;
    private String fullName;
    private Long attendanceId;

}
