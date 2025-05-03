package vn.bookstore.app.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Year;
import java.time.YearMonth;

@Getter
@Setter
public class PayrollStatisticsViewDTO {
    private YearMonth month;
    private Year year;

    private double averageWorkingDays;
    private int totalSickLeaves;
    private int totalMaternityLeaves;
    private int totalPaidLeaves;
    private int totalUnpaidLeaves;
    private int totalHolidayLeaves;

    private String avgGrossSalary;
    private String avgNetSalary;
    private String avgAllowance;
    private String avgPenalties;
    private String avgTax;
    private String costPerEmployee;

    private String totalGrossSalary;
    private String totalNetSalary;
    private String totalAllowance;
    private String totalPenalties;
    private String totalTax;
    private String totalEmployeeBHXH;
    private String totalEmployeeBHYT;
    private String totalEmployeeBHTN;
    private String totalSickBenefit;
    private String totalMaternityBenefit;
    private String totalCompanyCost;
}

