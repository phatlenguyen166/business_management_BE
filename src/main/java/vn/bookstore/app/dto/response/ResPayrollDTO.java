package vn.bookstore.app.dto.response;

import java.math.BigDecimal;

public class ResPayrollDTO {
    private Long id;
    private String idString;
    private int standardWorkingDays;
    private BigDecimal maternityBenefit;
    private BigDecimal netSalary;
    private BigDecimal tax;
    private BigDecimal penalties;
    private BigDecimal grossSalary;
    private BigDecimal allowance;
}
