package vn.bookstore.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.bookstore.app.util.constant.GenderEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {
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
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int status;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "user")
    private List<Bill> bills;

    @OneToMany(mappedBy = "user")
    private List<Payroll> payrolls;

    @OneToMany(mappedBy = "user")
    private List<LeaveRequest> leaveRequest;

    @OneToMany(mappedBy = "user")
    private List<AttendanceDetail> attendanceDetails;


    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
