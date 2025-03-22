package vn.bookstore.app.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
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
import java.text.DecimalFormat;
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
                                           int totalWorkingDays,int totalPaidLeaves, BigDecimal otherAllowances ) {
        BigDecimal totalBaseSalary = baseSalary.multiply(BigDecimal.valueOf(salaryCoefficient));
        BigDecimal dailySalary = totalBaseSalary.divide(BigDecimal.valueOf(standardWorkingDays), 4, RoundingMode.HALF_UP);
        int totalLeaveDays = standardWorkingDays - (totalWorkingDays + totalPaidLeaves);
        BigDecimal actualBasicSalary =  totalBaseSalary.subtract(dailySalary.multiply(BigDecimal.valueOf(totalLeaveDays)));
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
            BigDecimal grossSalary = calculateGrossSalary(baseSalary, salaryCoefficient, standardWorkingDays, totalWorkingDays, attendance.getTotalPaidLeaves(),otherAllowances);
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

    public BigDecimal calculateDeductions(BigDecimal employeeBHXH, BigDecimal employeeBHTN, BigDecimal employeeBHYT, BigDecimal penalties, BigDecimal tax) {
        return employeeBHTN.add(employeeBHXH).add(employeeBHYT).add(penalties).add(tax);
    }
    public BigDecimal calculateBenefit(BigDecimal maternityBenefit, BigDecimal sickLeaveBenefit ) {
        return maternityBenefit.add(sickLeaveBenefit);
    }

    public String getFormatTotalBaseSalary(Payroll payroll) {
        BigDecimal total = payroll.getBaseSalary().multiply(BigDecimal.valueOf(payroll.getSalaryCoefficient()));
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(total);
    }

    public String getFormatBigDecimal(BigDecimal bigDecimal) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(bigDecimal);
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

    public void headerPdf(Document document, Font boldFont, Font font ){
        Paragraph company = new Paragraph("Công ty: Inverse Enterprise", boldFont);
        company.setAlignment(Element.ALIGN_CENTER);
        document.add(company);

        Paragraph address = new Paragraph("Địa chỉ: 273 An Dương Vương, Phường 3, Quận 5, Thành phố Hồ Chí Minh", font);
        address.setAlignment(Element.ALIGN_CENTER);
        document.add(address);

        Paragraph title = new Paragraph("PHIẾU LƯƠNG", boldFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingBefore(15);
        title.setSpacingAfter(15);
        document.add(title);
    }

    public void contentPdf(Document document, ResPayrollDTO payroll, Font boldFont, Font font ) {
        Paragraph date = new Paragraph("Kỳ lương: " + payroll.getMonthOfYear(), font);
        date.setAlignment(Element.ALIGN_CENTER);
        date.setSpacingAfter(10);
        document.add(date);
        // Tách thông tin nhân viên thành hai bảng nằm ngang
        // Bảng 1: Thông tin nhân viên
        PdfPTable employeeInfoTable = new PdfPTable(2); // 2 cột: nhãn và giá trị
        employeeInfoTable.setWidthPercentage(200);
        employeeInfoTable.setWidths(new float[]{1.5f, 2f});
        addInfoRow(employeeInfoTable, "Mã Nhân Viên: ", payroll.getUserIdStr(), font);
        addInfoRow(employeeInfoTable, "Họ và Tên: ", payroll.getFullName(), font);
        addInfoRow(employeeInfoTable, "Chức Danh: ", payroll.getRoleName(), font);
        addInfoRow(employeeInfoTable, "Lương Cơ bản: ", payroll.getBaseSalary(), font);
        addInfoRow(employeeInfoTable, "Hệ số lương: ", String.valueOf(payroll.getSalaryCoefficient()), font);
        addInfoRow(employeeInfoTable, "Ngày công chuẩn: ", String.valueOf(payroll.getStandardWorkingDays()), font);

        // Bảng 2: Thông tin ngày công
        PdfPTable attendanceInfoTable = new PdfPTable(2); // 2 cột: nhãn và giá trị
        attendanceInfoTable.setWidthPercentage(200);
        attendanceInfoTable.setWidths(new float[]{1.5f, 1f});

        addInfoRow(attendanceInfoTable, "Ngày công đi làm: ", String.valueOf(payroll.getTotalWorkingDays()), font);
        addInfoRow(attendanceInfoTable, "Ngày nghỉ lễ: ", String.valueOf(payroll.getTotalHolidayLeaves()), font);
        addInfoRow(attendanceInfoTable, "Ngày nghỉ phép: ", String.valueOf(payroll.getTotalPaidLeaves()), font);
        addInfoRow(attendanceInfoTable, "Nghỉ ốm: ", String.valueOf(payroll.getTotalSickLeaves()), font);
        addInfoRow(attendanceInfoTable, "Nghỉ thai sản: ", String.valueOf(payroll.getTotalMaternityLeaves()), font);
        addInfoRow(attendanceInfoTable, "Nghỉ không phép: ", String.valueOf(payroll.getTotalUnpaidLeaves()), font);

        // Đặt hai bảng cạnh nhau
        PdfPTable infoTables = new PdfPTable(2);
        infoTables.setWidthPercentage(80);
        infoTables.setHorizontalAlignment(Element.ALIGN_CENTER);
        infoTables.setWidths(new float[]{2f, 1.5f});

        PdfPCell employeeCell = new PdfPCell(employeeInfoTable);
        employeeCell.setBorder(PdfPCell.NO_BORDER);
        infoTables.addCell(employeeCell);

        PdfPCell attendanceCell = new PdfPCell(attendanceInfoTable);
        attendanceCell.setBorder(PdfPCell.NO_BORDER);
        infoTables.addCell(attendanceCell);
        infoTables.setSpacingAfter(20);
        document.add(infoTables);

        // Bảng thu nhập
        PdfPTable incomeTable = new PdfPTable(2);
        incomeTable.setWidthPercentage(100);
        incomeTable.setWidths(new float[]{1f, 3f});

        // Header bảng thu nhập
        addTableHeader(incomeTable, "STT", "Các Khoản Thu Nhập", boldFont);

        // Nội dung bảng thu nhập
        addTableRow(incomeTable, "1", "Lương Chính: " + payroll.getMainSalary(), font);
        addTableRow(incomeTable, "2", "Phụ Cấp: " + payroll.getAllowance(), font);

        // Tổng cộng bảng thu nhập
        addTotalRow(incomeTable, "Tổng Cộng: " + payroll.getGrossSalary(), font);

        // Thêm khoảng cách sau bảng thu nhập
        incomeTable.setSpacingAfter(15);
        document.add(incomeTable);

        // Bảng khấu trừ
        PdfPTable deductionsTable = new PdfPTable(2);
        deductionsTable.setWidthPercentage(100);
        deductionsTable.setWidths(new float[]{1f, 3f});

        // Header bảng khấu trừ
        addTableHeader(deductionsTable, "STT", "Các Khoản Trừ Vào Lương", boldFont);

        // Nội dung bảng khấu trừ
        addTableRow(deductionsTable,"1", "Bảo Hiểm Bắt Buộc", font);
        addSubTableRow(deductionsTable, "1.1", "Bảo hiểm xã hội (8%): " + payroll.getEmployeeBHXH(), font);
        addSubTableRow(deductionsTable, "1.2", "Bảo hiểm y tế (1.5%): " + payroll.getEmployeeBHYT(), font);
        addSubTableRow(deductionsTable, "1.3", "Bảo hiểm thất nghiệp (1%): " + payroll.getEmployeeBHTN(), font);
        addTableRow(deductionsTable,"2", "Thuế TNCN: " + payroll.getTax(), font);
        addTableRow(deductionsTable,"3", "Phạt: " + payroll.getPenalties(), font);
        addTableRow(deductionsTable,"4", "Khác: ", font);

        // Tổng cộng bảng khấu trừ
        addTotalRow(deductionsTable, "Tổng Cộng: " + payroll.getDeductions() , font);
        deductionsTable.setSpacingAfter(15);
        document.add(deductionsTable);


        PdfPTable allowanceTable = new PdfPTable(2);
        allowanceTable.setWidthPercentage(100);
        allowanceTable.setWidths(new float[]{1f, 3f});

        addTableHeader(allowanceTable, "STT", "Các Khoản Phụ Cấp BHXH", boldFont);
        // Nội dung bảng khấu trừ
        addTableRow(allowanceTable,"1", "Phụ cấp thai sản: " + payroll.getMaternityBenefit(), font);
        addTableRow(allowanceTable,"2", "Phụ cấp nghỉ ốm: " + payroll.getSickBenefit(), font);
        // Tổng cộng bảng khấu trừ
        addTotalRow(allowanceTable, "Tổng Cộng: " + payroll.getTotalBenefit(), font);
        document.add(allowanceTable);

        // Tổng lương thực nhận
        Paragraph netSalary = new Paragraph("Tổng Số Tiền Lương Thực Nhận: " + payroll.getTotalIncome(), boldFont);
        netSalary.setSpacingBefore(15);
        document.add(netSalary);
    }

    public void footerPdf(Document document, Font boldFont, Font font){
        PdfPTable signatureTable = new PdfPTable(2);
        signatureTable.setWidthPercentage(100);
        signatureTable.setSpacingBefore(20);

        addSignatureCell(signatureTable, "Người lập phiếu\n(Ký và ghi rõ họ tên)", font);
        addSignatureCell(signatureTable, "Người nhận tiền\n(Ký và ghi rõ họ tên)", font);

        document.add(signatureTable);
    }

    @Override
    public void generatePayrollPdf(String filePath, ResPayrollDTO payroll) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        BaseFont bf = BaseFont.createFont(
                "fonts/times.ttf",      // Đường dẫn tương đối từ thư mục resources
                BaseFont.IDENTITY_H,    // Hỗ trợ Unicode
                BaseFont.EMBEDDED       // Nhúng font vào PDF
        );
        Font font = new Font(bf, 11);
        Font boldFont = new Font(bf, 12, Font.BOLD);

        headerPdf(document,boldFont,font);
        contentPdf(document,payroll,boldFont,font);
        footerPdf(document,boldFont,font);

        document.close();
    }

    @Override
    public void generatePayrollByYearPdf(String filePath, List<ResPayrollDTO> Payrolls) throws IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        BaseFont bf = BaseFont.createFont(
                "fonts/times.ttf",      // Đường dẫn tương đối từ thư mục resources
                BaseFont.IDENTITY_H,    // Hỗ trợ Unicode
                BaseFont.EMBEDDED       // Nhúng font vào PDF
        );
        Font font = new Font(bf, 11);
        Font boldFont = new Font(bf, 12, Font.BOLD);

        headerPdf(document,boldFont,font);

        for (ResPayrollDTO payroll : Payrolls) {
            contentPdf(document,payroll,boldFont,font);
            Paragraph spacing = new Paragraph(" ");
            spacing.setSpacingAfter(50f); // Điều chỉnh số theo nhu cầu
            document.add(spacing);
        }
        footerPdf(document,boldFont,font);
        document.close();
    }

    // Các phương thức hỗ trợ
    private void addInfoRow(PdfPTable table, String label, String value, Font font) {
        table.addCell(createCellNoBorder(label, font));
        table.addCell(createCellNoBorder(value, font));
    }

    private void addTableHeader(PdfPTable table, String col1, String col2, Font font) {
        table.addCell(createCellWithBorder(col1, font));
        table.addCell(createCellWithBorder(col2, font));
    }

    private void addTableRow(PdfPTable table, String col1, String col2, Font font) {
        table.addCell(createCellWithBorder(col1, font));
        table.addCell(createCellWithBorder(col2, font));
    }

    private void addSubTableRow(PdfPTable table, String col1, String col2, Font font) {
        table.addCell(createCellWithBorder("   " + col1, font)); // Thụt lề
        table.addCell(createCellWithBorder(col2, font));
    }

    private void addTotalRow(PdfPTable table, String value, Font font) {
        table.addCell(createCellWithBorder("", font));
        table.addCell(createCellWithBorder(value, font));
    }

    private void addSignatureCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = createCellWithBorder(text, font);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingTop(20);
        table.addCell(cell);
    }

    private PdfPCell createCellWithBorder(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBorder(PdfPCell.BOX);
        cell.setBorderWidth(0.5f);
        cell.setPadding(5);
        return cell;
    }

    private PdfPCell createCellNoBorder(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPadding(5);
        return cell;
    }

}
