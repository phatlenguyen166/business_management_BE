package vn.bookstore.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vn.bookstore.app.util.constant.LeaveTypeEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave_requests")
@Getter
@Setter
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title không được để trống")
    @Size(max = 255, message = "Title không được quá 255 ký tự")
    private String title;

    @NotNull(message = "Start date không được để trống")
    @FutureOrPresent(message = "Start date phải là ngày hiện tại hoặc tương lai")
    private LocalDate startDate;

    @NotNull(message = "End date không được để trống")
    @Future(message = "End date phải là ngày trong tương lai")
    private LocalDate endDate;
    private long totalDayLeave;
    @CreationTimestamp
    private LocalDateTime sendDate;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    @NotNull(message = "Lý do nghỉ không được để trống")
    @Enumerated(EnumType.STRING)
    private LeaveTypeEnum leaveReason;

    private int status;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
