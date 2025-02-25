package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.util.constant.GenderEnum;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name ="users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String address;
    private GenderEnum gender;
    private String username;
    private String password;
    private Instant lastLogin;
    private Instant createdAt;
    private Instant updatedAt;
    private String refreshToken;
    private int status;

    @ManyToOne
    @JoinColumn(name = "seniority_level_id")
    private SeniorityLevel seniorityLevel;

    @OneToMany(mappedBy = "user")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "user")
    private List<Bill> bills;

    @OneToMany(mappedBy = "user")
    private List<Payroll> payrolls;

    @OneToMany(mappedBy = "user")
    private List<LeaveRequest> leaveRequest;

    @OneToMany(mappedBy = "user")
    private List<Attendance> attendances;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.updatedAt = Instant.now();
    }
}
