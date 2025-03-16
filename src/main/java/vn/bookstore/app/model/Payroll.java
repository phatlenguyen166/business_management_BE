package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payrolls")
@Getter
@Setter
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal maternityBenefit;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal netSalary;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal tax;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal penalties;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal grossSalary;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal allowance;

    @OneToOne
    @JoinColumn(name = "attendance_id", unique = true, nullable = false)
    private Attendance attendance;




}
