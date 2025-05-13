package vn.bookstore.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.response.PayrollStatisticsDTO;
import vn.bookstore.app.dto.response.PayrollStatisticsViewDTO;
import vn.bookstore.app.mapper.SalaryStatisticMapper;
import vn.bookstore.app.model.Payroll;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.PayrollRepository;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.PayrollStatisticsService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PayrollStatisticsServiceImpl implements PayrollStatisticsService {
    private final PayrollRepository payrollRepository;
    private final UserRepository userRepository;
    private final SalaryStatisticMapper salaryStatisticMapper;

    @Override
    public PayrollStatisticsViewDTO getMonthlyStatistics(YearMonth month) {
        // 1) Lấy tất cả payroll của tháng
        List<Payroll> payrolls = payrollRepository
                .findAllByAttendance_MonthOfYear(month.toString());

        return this.salaryStatisticMapper.convertToPayrollStatisticsViewDTO(assembleStatistics(month, null, payrolls));
    }

    @Override
    public PayrollStatisticsViewDTO getYearlyStatistics(Year year) {
        // 1) Lấy tất cả payroll của năm
        List<Payroll> payrolls = payrollRepository
                .findAllPayrollByYear(year.toString()); // ví dụ "2025-%"
        return this.salaryStatisticMapper.convertToPayrollStatisticsViewDTO(assembleStatistics(null, year, payrolls)) ;
    }

    @Override
    public List<PayrollStatisticsViewDTO> getMonthlyStatisticsByYear(Year year) {
        // Lặp qua từng tháng trong năm và lấy thống kê
        return IntStream.rangeClosed(1, 12) // 12 tháng trong năm
                .mapToObj(month -> YearMonth.of(year.getValue(), month)) // Tạo các đối tượng YearMonth
                .map(this::getMonthlyStatistics) // Gọi hàm getMonthlyStatistics
                .collect(Collectors.toList()); // Thu thập kết quả vào List
    }



    private PayrollStatisticsDTO assembleStatistics(YearMonth month, Year year, List<Payroll> payrolls) {
        PayrollStatisticsDTO dto = new PayrollStatisticsDTO();
        dto.setMonth(month);
        dto.setYear(year);

        int count = payrolls.size();
        // trung bình ngày công
        double avgWorkingDays = payrolls.stream()
                .map(p -> p.getAttendance().getTotalWorkingDays())
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        dto.setAverageWorkingDays(avgWorkingDays);

        // tổng các leave-days
        dto.setTotalSickLeaves(
                payrolls.stream().map(p -> p.getAttendance().getTotalSickLeaves()).mapToInt(i->i).sum());
        dto.setTotalMaternityLeaves(
                payrolls.stream().map(p -> p.getAttendance().getTotalMaternityLeaves()).mapToInt(i->i).sum());
        dto.setTotalPaidLeaves(
                payrolls.stream().map(p -> p.getAttendance().getTotalPaidLeaves()).mapToInt(i->i).sum());
        dto.setTotalUnpaidLeaves(
                payrolls.stream().map(p -> p.getAttendance().getTotalUnpaidLeaves()).mapToInt(i->i).sum());
        dto.setTotalHolidayLeaves(
                payrolls.stream().map(p -> p.getAttendance().getTotalHolidayLeaves()).mapToInt(i->i).sum());

        // trung bình gross, net, allowance, penalties, tax
        dto.setAvgGrossSalary(avg(payrolls, Payroll::getGrossSalary));
        dto.setAvgNetSalary(avg(payrolls, Payroll::getNetSalary));
        dto.setAvgAllowance(avg(payrolls, Payroll::getAllowance));
        dto.setAvgPenalties(avg(payrolls, Payroll::getPenalties));
        dto.setAvgTax(avg(payrolls, Payroll::getTax));

        // tổng hợp
        dto.setTotalGrossSalary(sum(payrolls, Payroll::getGrossSalary));
        dto.setTotalNetSalary(sum(payrolls, Payroll::getNetSalary));
        dto.setTotalAllowance(sum(payrolls, Payroll::getAllowance));
        dto.setTotalPenalties(sum(payrolls, Payroll::getPenalties));
        dto.setTotalTax(sum(payrolls, Payroll::getTax));
        dto.setTotalEmployeeBHXH(sum(payrolls, Payroll::getEmployeeBHXH));
        dto.setTotalEmployeeBHYT(sum(payrolls, Payroll::getEmployeeBHYT));
        dto.setTotalEmployeeBHTN(sum(payrolls, Payroll::getEmployeeBHTN));
        dto.setTotalSickBenefit(sum(payrolls, Payroll::getSickBenefit));
        dto.setTotalMaternityBenefit(sum(payrolls, Payroll::getMaternityBenefit));

        // chi phí công ty
        BigDecimal totalCompanyCost = payrolls.stream()
                .map(Payroll::getTotalCompanyCost) // giả sử bạn có phương thức này
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalCompanyCost(totalCompanyCost);

        // chi phí bình quân 1 nhân viên
        if (count > 0) {
            dto.setCostPerEmployee(totalCompanyCost.divide(BigDecimal.valueOf(count), 0, BigDecimal.ROUND_HALF_UP));
        } else {
            dto.setCostPerEmployee(BigDecimal.ZERO);
        }

        return dto;
    }

    private BigDecimal sum(List<Payroll> list, java.util.function.Function<Payroll,BigDecimal> mapper) {
        return list.stream()
                .map(mapper)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal avg(List<Payroll> list, java.util.function.Function<Payroll,BigDecimal> mapper) {
        if (list.isEmpty()) return BigDecimal.ZERO;
        BigDecimal total = sum(list, mapper);
        return total.divide(BigDecimal.valueOf(list.size()), 0, BigDecimal.ROUND_HALF_UP);
    }
}
