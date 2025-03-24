package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "payrolls")
@Getter
@Setter
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal maternityBenefit;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal sickBenefit;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal netSalary;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal grossSalary;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal tax;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal employeeBHXH;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal employeeBHYT;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal employeeBHTN;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal penalties;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal allowance;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal totalIncome;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal totalCompanyCost;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal baseSalary;
    private float salaryCoefficient;


    @OneToOne
    @JoinColumn(name = "attendance_id", referencedColumnName = "id", unique = true, nullable = false)
    private Attendance attendance;






}
