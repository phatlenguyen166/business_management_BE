package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.util.constant.AttendanceStatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_details")
@Getter
@Setter
public class AttendanceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate workingDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private AttendanceStatusEnum attendanceStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;





}
