package vn.bookstore.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "seniority_levels")
@Getter
@Setter
public class SeniorityLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên cấp bậc không được để trống")
    @Size(max = 100, message = "Tên cấp bậc không được quá 100 ký tự")
    private String levelName;

    @Column(columnDefinition = "MEDIUMTEXT")
    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 500, message = "Mô tả không được quá 500 ký tự")
    private String description;

    @Positive(message = "Hệ số lương phải lớn hơn 0")
    @Max(value = 5, message = "Hệ số lương không được vượt quá 5")
    private float salaryCoefficient;
    private int status;

    @JsonIgnore
    @OneToMany(mappedBy = "seniorityLevel")
    private List<Contract> contracts;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}
