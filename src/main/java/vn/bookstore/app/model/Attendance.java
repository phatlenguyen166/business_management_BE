package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.util.constant.AttendanceStatusEnum;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "attendances")
@Getter
@Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate workingDate;
    private Instant checkIn;
    private Instant checkOut;
    private AttendanceStatusEnum attendanceStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;





}
