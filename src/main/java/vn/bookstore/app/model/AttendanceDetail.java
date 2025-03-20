    package vn.bookstore.app.model;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    import vn.bookstore.app.util.constant.AttendanceStatusEnum;
    import vn.bookstore.app.util.constant.LateTypeEnum;
    import vn.bookstore.app.util.constant.LeaveTypeEnum;

    import java.time.LocalDate;
    import java.time.LocalTime;

    @Entity
    @Table(name = "attendance_details")
    @Getter
    @Setter
    public class AttendanceDetail {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private LocalDate workingDay;
        private LocalTime checkIn;
        private LocalTime checkOut;
        @Enumerated(EnumType.STRING)
        private AttendanceStatusEnum attendanceStatus;

        @Enumerated(EnumType.STRING)
        private LeaveTypeEnum leaveTypeEnum;

        @Enumerated(EnumType.STRING)
        private LateTypeEnum lateTypeEnum;

        @ManyToOne
        @JoinColumn(name = "attendance_id", nullable = false)
        private Attendance attendance;

        @ManyToOne
        @JoinColumn(name = "holiday_id")
        private Holiday holiday;



    }
