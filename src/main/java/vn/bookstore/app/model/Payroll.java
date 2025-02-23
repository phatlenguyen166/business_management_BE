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
    private LocalDate payPeriod;
    private int workingDays;
    private int maternityLeaves;
    private int sickLeaves;
    private int paidLeaves;
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
    private BigDecimal mealAllowance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



}
