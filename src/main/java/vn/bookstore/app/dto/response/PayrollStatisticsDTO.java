package vn.bookstore.app.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Year;
import java.time.YearMonth;

@Getter
@Setter
public class PayrollStatisticsDTO {
    // Khoá báo cáo
    private YearMonth month;
    private Year year;

    // Nhân sự & ngày công
    private double averageWorkingDays;
    private int totalSickLeaves;
    private int totalMaternityLeaves;
    private int totalPaidLeaves;
    private int totalUnpaidLeaves;
    private int totalHolidayLeaves;

    // Tiền lương trung bình
    private BigDecimal avgGrossSalary;
    private BigDecimal avgNetSalary;
    private BigDecimal avgAllowance;
    private BigDecimal avgPenalties;
    private BigDecimal avgTax;
    private BigDecimal costPerEmployee;

    // Tổng hợp
    private BigDecimal totalGrossSalary;
    private BigDecimal totalNetSalary;
    private BigDecimal totalAllowance;
    private BigDecimal totalPenalties;
    private BigDecimal totalTax;
    private BigDecimal totalEmployeeBHXH;
    private BigDecimal totalEmployeeBHYT;
    private BigDecimal totalEmployeeBHTN;
    private BigDecimal totalSickBenefit;
    private BigDecimal totalMaternityBenefit;
    private BigDecimal totalCompanyCost;

}
