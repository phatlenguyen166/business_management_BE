package vn.bookstore.app.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


import org.mapstruct.Named;
import vn.bookstore.app.model.Payroll;
import vn.bookstore.app.model.User;
import vn.bookstore.app.service.impl.ContractServiceImpl;
import vn.bookstore.app.service.impl.PayrollServiceImpl;

import java.math.BigDecimal;

@Component

public class PayrollMapperHelper {
    private final PayrollServiceImpl payrollService;

    public PayrollMapperHelper(@Lazy PayrollServiceImpl payrollService) {
        this.payrollService = payrollService;
    }


    @Named("formatTotalBaseSalary")
    public String formatTotalBaseSalary(Payroll payroll) {
        return this.payrollService.getFormatTotalBaseSalary(payroll);
    }

    @Named("formatDeductions")
    public String formatDeductions(Payroll payroll) {
        BigDecimal total = this.payrollService.calculateDeductions(payroll.getEmployeeBHXH(),payroll.getEmployeeBHTN(),payroll.getEmployeeBHYT(),payroll.getPenalties(),payroll.getTax());
        return this.payrollService.getFormatBigDecimal(total);
    }

    @Named("formatTotalBenefit")
    public String formatTotalBenefit(Payroll payroll) {
        BigDecimal total = this.payrollService.calculateBenefit(payroll.getMaternityBenefit(),payroll.getSickBenefit());
        return this.payrollService.getFormatBigDecimal(total);
    }
    @Named("formatMainSalary")
    public String formatMainSalary(Payroll payroll) {
        BigDecimal total = this.payrollService.calculateGrossSalary(payroll.getBaseSalary(),
                payroll.getSalaryCoefficient(),payroll.getAttendance().getStandardWorkingDays(),
                payroll.getAttendance().getTotalWorkingDays(),payroll.getAttendance().getTotalPaidLeaves(), payroll.getAllowance());
        return this.payrollService.getFormatBigDecimal(total.subtract(payroll.getAllowance()));
    }

    @Named("formatBigDecimal")
    public String formatBigDecimal(BigDecimal bigDecimal) {
        return this.payrollService.getFormatBigDecimal(bigDecimal);
    }
}
