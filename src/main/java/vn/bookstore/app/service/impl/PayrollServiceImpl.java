package vn.bookstore.app.service.impl;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.response.ResPayrollDTO;
import vn.bookstore.app.mapper.PayrollMapper;
import vn.bookstore.app.model.*;
import vn.bookstore.app.repository.*;
import vn.bookstore.app.service.PayrollService;
import vn.bookstore.app.util.constant.LateTypeEnum;
import vn.bookstore.app.util.error.InvalidRequestException;
import vn.bookstore.app.util.error.NotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {
    private final HolidayRepository holidayRepository;
    private final UserRepository userRepository;
    private final SeniorityLevelRepository seniorityLevelRepository;
    private final ContractRepository contractRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceDetailRepository attendanceDetailRepository;
    private final PayrollRepository payrollRepository;
    private final PayrollMapper payrollMapper;

    public boolean isHoliday(LocalDate date, List<Holiday> holidays) {
        for (Holiday holiday : holidays) {
            if (!date.isBefore(holiday.getStartDate()) && !date.isAfter(holiday.getEndDate())) {
                return true;
            }
        }
        return false;
    }

    public int getStandardWorkingDays(YearMonth yearMonth) {
        int workingDays = 0;
        List<Holiday> holidays = holidayRepository.findAllByStatus(1);
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = yearMonth.atDay(day);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY && !isHoliday(date, holidays)) {
                workingDays++;
            }
        }
        System.out.println(workingDays);
        return workingDays;

    }



    public BigDecimal calPenalties(BigDecimal baseSalary, int standardWorkingDays, float salaryConfident, Attendance attendance) {
        BigDecimal dailySalary = baseSalary.multiply(BigDecimal.valueOf(salaryConfident))
                .divide(BigDecimal.valueOf(standardWorkingDays), 4, RoundingMode.HALF_UP);
        List<AttendanceDetail> attendanceDetails = this.attendanceDetailRepository.findAllByAttendance(attendance);
        Map<LateTypeEnum, Integer> lateCounts = new EnumMap<>(LateTypeEnum.class);
        for (LateTypeEnum lateTypeEnum : LateTypeEnum.values()) {
            lateCounts.put(lateTypeEnum, 0);
        }
        for (AttendanceDetail attendanceDetail : attendanceDetails) {
            if (attendanceDetail.getLateTypeEnum() != null) {
                lateCounts.put(attendanceDetail.getLateTypeEnum(), lateCounts.get(attendanceDetail.getLateTypeEnum()) + 1);
            }
        }
        int late_1 = lateCounts.getOrDefault(LateTypeEnum.LATE_1, 0);
        int late_2 = lateCounts.getOrDefault(LateTypeEnum.LATE_2, 0);
        int late_3 = lateCounts.getOrDefault(LateTypeEnum.LATE_3, 0);
        int late_4 = lateCounts.getOrDefault(LateTypeEnum.LATE_4, 0);

        BigDecimal penalty = BigDecimal.ZERO;
        penalty = penalty.add(dailySalary.multiply(BigDecimal.valueOf(late_1 * 0.125)));
        penalty = penalty.add(dailySalary.multiply(BigDecimal.valueOf(late_2 * 0.25)));
        penalty = penalty.add(dailySalary.multiply(BigDecimal.valueOf(late_3 * 0.6)));
        penalty = penalty.add(dailySalary.multiply(BigDecimal.valueOf(late_4)));
        return penalty;
    }

    public BigDecimal calculateGrossSalary(BigDecimal baseSalary, float salaryCoefficient, int standardWorkingDays,
                                           int totalWorkingDays, BigDecimal otherAllowances, int totalPaidLeaves) {
        BigDecimal totalBaseSalary = baseSalary.multiply(BigDecimal.valueOf(salaryCoefficient));
        BigDecimal dailySalary = totalBaseSalary.divide(BigDecimal.valueOf(standardWorkingDays), 4, RoundingMode.HALF_UP);
        int totalLeaveDays = standardWorkingDays - (totalWorkingDays + totalPaidLeaves);
        BigDecimal actualBasicSalary = totalBaseSalary.subtract(dailySalary.multiply(BigDecimal.valueOf(totalLeaveDays)));
        return actualBasicSalary.add(otherAllowances);
    }

    public BigDecimal calculateCompanyBHXH(BigDecimal grossSalary) {
        return grossSalary.multiply(BigDecimal.valueOf(0.175)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateCompanyBHYT(BigDecimal grossSalary) {
        return grossSalary.multiply(BigDecimal.valueOf(0.03)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateCompanyBHTN(BigDecimal grossSalary) {
        return grossSalary.multiply(BigDecimal.valueOf(0.01)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTotalCompanyCost(BigDecimal grossSalary) {
        return grossSalary.add(calculateCompanyBHXH(grossSalary))
                .add(calculateCompanyBHYT(grossSalary))
                .add(calculateCompanyBHTN(grossSalary));
    }

    public BigDecimal calculateEmployeeBHXH(BigDecimal grossSalary) {
        return grossSalary.multiply(BigDecimal.valueOf(0.08)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateEmployeeBHYT(BigDecimal grossSalary) {
        return grossSalary.multiply(BigDecimal.valueOf(0.015)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateEmployeeBHTN(BigDecimal grossSalary) {
        return grossSalary.multiply(BigDecimal.valueOf(0.01)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTotalEmployeeDeductions(BigDecimal grossSalary) {
        return calculateEmployeeBHXH(grossSalary)
                .add(calculateEmployeeBHYT(grossSalary))
                .add(calculateEmployeeBHTN(grossSalary));
    }

    public BigDecimal calculateNetSalary(BigDecimal grossSalary, BigDecimal tax, BigDecimal penalty) {
        BigDecimal employeeDeductions = calculateTotalEmployeeDeductions(grossSalary);
        return grossSalary.subtract(employeeDeductions).subtract(tax).subtract(penalty);
    }

    public BigDecimal calculateSickLeaveBenefit(BigDecimal baseSalary, float salaryConfident, int standardWorkingDays, int sickLeaveDays, float rate) {

        BigDecimal dailySalary = baseSalary.multiply(BigDecimal.valueOf(salaryConfident))
                .divide(BigDecimal.valueOf(standardWorkingDays), 4, RoundingMode.HALF_UP);
        return dailySalary.multiply(BigDecimal.valueOf(sickLeaveDays))
                .multiply(BigDecimal.valueOf(rate))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calAverageSocialInsuranceSalary(User user) {
        List<Attendance> attendances = this.attendanceRepository.findAllByUserOrderByMonthOfYearDesc(user);
        if (attendances.size() < 6) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal sum = BigDecimal.ZERO;
            int count = 0;
            for (int i = 0; i < 6; i++) {
                Attendance a = attendances.get(i);
                Payroll payroll = this.payrollRepository.findPayrollByAttendance(a);
                if (payroll != null && payroll.getGrossSalary() != null) {
                    sum = sum.add(payroll.getGrossSalary());
                    count++;
                }
            }
            if (count == 0) {
                return BigDecimal.ZERO;
            }
            return sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal calculateMaternityBenefit(BigDecimal averageSocialInsuranceSalary, int maternityLeaveDays, int standardWorkingDays) {
        return averageSocialInsuranceSalary.divide(BigDecimal.valueOf(standardWorkingDays), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(maternityLeaveDays))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTotalIncome(BigDecimal netSalary, BigDecimal sickLeaveBenefit, BigDecimal maternityBenefit) {
        return netSalary.add(sickLeaveBenefit).add(maternityBenefit).setScale(2, RoundingMode.HALF_UP);
    }


    // taxableIncome : Thu nhập chịu thuế =  Thu nhập trước thuế - (Giảm trừ gia cảnh bản thân	+  Giảm trừ gia cảnh người phụ thuộc)
    public BigDecimal calTax(BigDecimal taxableIncome) {
        if (taxableIncome.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        if (taxableIncome.compareTo(new BigDecimal("5000000")) <= 0) {
            return taxableIncome.multiply(new BigDecimal("0.05")).setScale(2, RoundingMode.HALF_UP);
        } else if (taxableIncome.compareTo(new BigDecimal("10000000")) <= 0) {
            return taxableIncome.multiply(new BigDecimal("0.10"))
                    .subtract(new BigDecimal("250000"))
                    .setScale(2, RoundingMode.HALF_UP);
        } else if (taxableIncome.compareTo(new BigDecimal("18000000")) <= 0) {
            return taxableIncome.multiply(new BigDecimal("0.15"))
                    .subtract(new BigDecimal("750000"))
                    .setScale(2, RoundingMode.HALF_UP);
        } else if (taxableIncome.compareTo(new BigDecimal("32000000")) <= 0) {
            return taxableIncome.multiply(new BigDecimal("0.20"))
                    .subtract(new BigDecimal("1650000"))
                    .setScale(2, RoundingMode.HALF_UP);
        } else if (taxableIncome.compareTo(new BigDecimal("52000000")) <= 0) {
            return taxableIncome.multiply(new BigDecimal("0.25"))
                    .subtract(new BigDecimal("3250000"))
                    .setScale(2, RoundingMode.HALF_UP);
        } else if (taxableIncome.compareTo(new BigDecimal("80000000")) <= 0) {
            return taxableIncome.multiply(new BigDecimal("0.30"))
                    .subtract(new BigDecimal("5850000"))
                    .setScale(2, RoundingMode.HALF_UP);
        } else {
            return taxableIncome.multiply(new BigDecimal("0.35"))
                    .subtract(new BigDecimal("9850000"))
                    .setScale(2, RoundingMode.HALF_UP);
        }
    }


    @Override
    public List<ResPayrollDTO> createPayroll(YearMonth yearMonth) {
        if (!this.payrollRepository.findAllByYearMonth(yearMonth.toString()).isEmpty()) {
            throw  new InvalidRequestException("Bảng lương tháng này đã được tạo");
        }
        List<Payroll> payrolls = new ArrayList<>();
        List<Attendance> attendances = this.attendanceRepository.findAllByMonthOfYear(yearMonth.toString());
        for (Attendance attendance : attendances) {
            Contract contract = this.contractRepository.findContractByUserAndStatus(attendance.getUser(), 1)
                    .orElseThrow(() -> new NotFoundException("Not found contract"));
            Payroll payroll = new Payroll();
            payroll.setAttendance(attendance);
            BigDecimal baseSalary = contract.getBaseSalary();
            payroll.setBaseSalary(baseSalary);
            float salaryCoefficient = contract.getSeniorityLevel().getSalaryCoefficient();
            payroll.setSalaryCoefficient(salaryCoefficient);
            int standardWorkingDays = getStandardWorkingDays(yearMonth);
            payroll.setStandardWorkingDays(standardWorkingDays);
            int totalWorkingDays = attendance.getTotalWorkingDays();
            BigDecimal otherAllowances = contract.getSeniorityLevel().getRole().getAllowance().getAllowance();
            payroll.setAllowance(otherAllowances);
            BigDecimal penalties = calPenalties(baseSalary, standardWorkingDays, salaryCoefficient, attendance);
            payroll.setPenalties(penalties);
            BigDecimal grossSalary = calculateGrossSalary(baseSalary, salaryCoefficient, standardWorkingDays, totalWorkingDays, otherAllowances,attendance.getTotalPaidLeaves());
            payroll.setGrossSalary(grossSalary);
            BigDecimal employeeBHXH = calculateEmployeeBHXH(grossSalary);
            payroll.setEmployeeBHXH(employeeBHXH);
            BigDecimal employeeBHTN = calculateEmployeeBHTN(grossSalary);
            payroll.setEmployeeBHTN(employeeBHTN);
            BigDecimal employeeBHYT = calculateEmployeeBHYT(grossSalary);
            payroll.setEmployeeBHYT(employeeBHYT);
            BigDecimal taxableIncome = grossSalary.subtract(employeeBHXH).subtract(employeeBHTN).subtract(employeeBHYT).subtract(new BigDecimal(11000000));
            BigDecimal tax = calTax(taxableIncome);
            payroll.setTax(tax);
            BigDecimal netSalary = calculateNetSalary(grossSalary, tax, penalties);
            payroll.setNetSalary(netSalary);
            BigDecimal totalCompanyCost = calculateTotalCompanyCost(grossSalary);
            payroll.setTotalCompanyCost(totalCompanyCost);
            BigDecimal averageSocialInsuranceSalary = calAverageSocialInsuranceSalary(attendance.getUser());
            BigDecimal maternityBenefit = calculateMaternityBenefit(averageSocialInsuranceSalary, attendance.getTotalMaternityLeaves(), standardWorkingDays);
            payroll.setMaternityBenefit(maternityBenefit);
            BigDecimal sickLeaveBenefit = calculateSickLeaveBenefit(baseSalary, salaryCoefficient, standardWorkingDays, attendance.getTotalSickLeaves(), 0.75f);
            payroll.setSickBenefit(sickLeaveBenefit);
            BigDecimal totalIncome = calculateTotalIncome(netSalary, sickLeaveBenefit, maternityBenefit);
            payroll.setTotalIncome(totalIncome);
            this.payrollRepository.save(payroll);
            payrolls.add(payroll);
        }
        return payrolls.stream().map(payrollMapper::convertToResPayrollDTO).collect(Collectors.toList());
    }

    @Override
    public List<ResPayrollDTO> getAllPayRoll() {
        List<Payroll> payrolls = this.payrollRepository.findAll();
        return payrolls.stream().map(payrollMapper::convertToResPayrollDTO).collect(Collectors.toList());
    }

    @Override
    public List<ResPayrollDTO> getAllPayRollByUser(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Người dùng không tồn tại"));
        return this.payrollRepository.findAllPayrollByUserOrderByMonthOfYear(user).stream()
                .map(payrollMapper::convertToResPayrollDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResPayrollDTO> getAllPayRollByUserByYear(Long userId, Year year) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Người dùng không tồn tại"));
        return this.payrollRepository.findAllPayrollByUserByYear(year.toString(),user).stream()
                .map(payrollMapper::convertToResPayrollDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResPayrollDTO getPayrollById(Long id) {
        Payroll payroll = this.payrollRepository.findPayrollById(id).orElseThrow(() -> new NotFoundException("Bảng lương không tồn tại"));
        return this.payrollMapper.convertToResPayrollDTO(payroll);
    }


    @Override
    public void generatePayrollPdf(String filePath, ResPayrollDTO payroll) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Tiêu đề báo cáo
        document.add(new Paragraph("BẢNG LƯƠNG"));
        document.add(new Paragraph(" ")); // Dòng trống

        // Thêm thông tin từ Payroll
        document.add(new Paragraph("Standard Working Days: " + payroll.getStandardWorkingDays()));
        document.add(new Paragraph("Gross Salary: " + payroll.getGrossSalary()));
        document.add(new Paragraph("Net Salary: " + payroll.getNetSalary()));
        document.add(new Paragraph("Tax: " + payroll.getTax()));
        document.add(new Paragraph("Maternity Benefit: " + payroll.getMaternityBenefit()));
        document.add(new Paragraph("Sick Benefit: " + payroll.getSickBenefit()));
        document.add(new Paragraph("Employee BHXH: " + payroll.getEmployeeBHXH()));
        document.add(new Paragraph("Employee BHYT: " + payroll.getEmployeeBHYT()));
        document.add(new Paragraph("Employee BHTN: " + payroll.getEmployeeBHTN()));
        document.add(new Paragraph("Penalties: " + payroll.getPenalties()));
        document.close();
    }
}
