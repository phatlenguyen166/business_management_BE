package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.util.constant.AttendanceStatusEnum;
import vn.bookstore.app.util.constant.LeaveTypeEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Entity
@Table(name = "attendances")
@Getter
@Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "month_of_year")
    private String monthOfYear;
    private int totalWorkingDays;
    private int totalSickLeaves;
    private int totalPaidLeaves;
    private int totalMaternityLeaves;
    private int totalUnpaidLeaves;
    private int totalHolidayLeaves;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "attendance")
    private List<AttendanceDetail> attendanceDetails;

    @OneToOne(mappedBy = "attendance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Payroll payroll;
}



