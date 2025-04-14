package vn.bookstore.app.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResPayrollDTO {

    private Long id;
    private String idString;
    private int standardWorkingDays;
    private String maternityBenefit;
    private String sickBenefit;
    private String netSalary;
    private String grossSalary;
    private String tax;
    private String employeeBHXH;
    private String employeeBHYT;
    private String employeeBHTN;
    private String penalties;
    private String allowance;
    private String totalIncome;
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
    private String baseSalary;
    private String totalBaseSalary;
    private String Deductions;
    private String totalBenefit;
    private String mainSalary;
}
