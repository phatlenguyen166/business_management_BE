package vn.bookstore.app.dto.response;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.model.Attendance;

import java.math.BigDecimal;

@Getter
@Setter
public class ResPayrollDTO {
    private Long id;
    private String idString;
    private int standardWorkingDays;
    private BigDecimal maternityBenefit;
    private BigDecimal sickBenefit;
    private BigDecimal netSalary;
    private BigDecimal grossSalary;
    private BigDecimal tax;
    private BigDecimal employeeBHXH;
    private BigDecimal employeeBHYT;
    private BigDecimal employeeBHTN;
    private BigDecimal penalties;
    private BigDecimal allowance;
    private BigDecimal totalIncome;
    private Long attendanceId;
    private String monthOfYear;
    private String userIdStr;
    private String fullName;
    private String roleName;
    private float salaryCoefficient;
    private int totalWorkingDays;
    private int totalSickLeaves;
    private int totalPaidLeaves;
    private int totalMaternityLeaves;
    private int totalUnpaidLeaves;
    private int totalHolidayLeaves;
    private BigDecimal baseSalary;
}
