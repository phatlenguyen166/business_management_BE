package vn.bookstore.app.controller;

import java.io.IOException;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.bookstore.app.dto.request.ReqPayrollDTO;
import vn.bookstore.app.dto.response.ResPayrollDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.impl.PayrollServiceImpl;
import vn.bookstore.app.util.error.InvalidRequestException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollServiceImpl payrollService;

    @PostMapping("/payrolls")
    public ResponseEntity<ResponseDTO<List<ResPayrollDTO>>> createPayroll(@Valid @RequestBody ReqPayrollDTO payrollDTO) {
        YearMonth yearMonth;
        try {
            yearMonth = YearMonth.parse(payrollDTO.getYearMonthStr());
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid yearMonth format. Expected format: yyyy-MM");
        }
        List<ResPayrollDTO> newPayrolls = this.payrollService.createPayroll(yearMonth);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        201,
                        true,
                        null,
                        "Create Payroll successfully",
                        newPayrolls
                )
        );
    }

    @GetMapping("/payrolls")
    public ResponseEntity<ResponseDTO<List<ResPayrollDTO>>> GetAllPayroll() {
        List<ResPayrollDTO> newPayrolls = this.payrollService.getAllPayRoll();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get All Payroll successfully",
                        newPayrolls
                )
        );
    }

    @GetMapping("/payrolls/user/{userId}")
    public ResponseEntity<ResponseDTO<List<ResPayrollDTO>>> GetAllPayrollByUser(@PathVariable Long userId) {
        List<ResPayrollDTO> newPayrolls = this.payrollService.getAllPayRollByUser(userId);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get All Payroll successfully",
                        newPayrolls
                )
        );
    }

    @GetMapping("/payrolls/user/year/{userId}")
    public ResponseEntity<ResponseDTO<List<ResPayrollDTO>>> GetAllPayrollByUserByYear(@PathVariable Long userId, @RequestParam("year") String yearStr) {
        Year year;
        try {
            year = Year.parse(yearStr);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid Year format. Expected format: yyyy");
        }
        List<ResPayrollDTO> newPayrolls = this.payrollService.getAllPayRollByUserByYear(userId, year);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get All Payroll successfully",
                        newPayrolls
                )
        );
    }

    @GetMapping("/payrolls/month")
    public ResponseEntity<ResponseDTO<List<ResPayrollDTO>>> GetAllPayrollByMonth(@RequestParam("yearMonth") String yearMonthStr) {
        YearMonth yearMonth;
        try {
            yearMonth = YearMonth.parse(yearMonthStr);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid Year format. Expected format: yyyy-mm");
        }
        List<ResPayrollDTO> Payrolls = this.payrollService.getAllPayRollByMonth(yearMonth);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get All Payroll successfully",
                        Payrolls
                )
        );
    }

    @GetMapping("/payrolls/{id}")
    public ResponseEntity<ResponseDTO<ResPayrollDTO>> GetAllPayrollById(@PathVariable Long id) {
        ResPayrollDTO payrollDTO = this.payrollService.getPayrollById(id);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get Payroll successfully",
                        payrollDTO
                )
        );
    }

    @GetMapping("/payrolls/export/{id}")
    public ResponseEntity<ResponseDTO<String>> exportPayroll(@PathVariable Long id) {
        ResPayrollDTO payrollDTO = this.payrollService.getPayrollById(id);
        String filePath = "PayrollReport_" + payrollDTO.getUserIdStr() + "_" + payrollDTO.getMonthOfYear() + ".pdf";
        try {
            String fullFilePath = payrollService.generatePayrollPdf(filePath, payrollDTO);
            return ResponseEntity.ok(
                    new ResponseDTO<>(
                            200,
                            true,
                            null,
                            "Reject LeaveRequest successfully",
                            fullFilePath
                    )
            );
        } catch (DocumentException | IOException e) {
            throw new InvalidRequestException(e.getMessage());
        }
    }

    @GetMapping("/payrolls/export/year/{userId}")
    public ResponseEntity<ResponseDTO<String>> exportPayrollByYear(@PathVariable Long userId, @RequestParam("year") String yearStr) {
        Year year;
        try {
            year = Year.parse(yearStr);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid Year format. Expected format: yyyy");
        }
        List<ResPayrollDTO> Payrolls = this.payrollService.getAllPayRollByUserByYear(userId, year);
        String filePath = "PayrollReport_NV-" + userId + "_" + year + ".pdf";
        try {
            String fullFilePath = payrollService.generatePayrollByYearPdf(filePath, Payrolls);
            return ResponseEntity.ok(
                    new ResponseDTO<>(
                            200,
                            true,
                            null,
                            "Reject LeaveRequest successfully",
                            fullFilePath
                    )
            );
        } catch (DocumentException | IOException e) {
            throw new InvalidRequestException(e.getMessage());
        }
    }

    @GetMapping("/payrolls/year")
    public ResponseEntity<ResponseDTO<List<ResPayrollDTO>>> getAllPayrollByYear(@RequestParam("year") String yearStr) {
        Year year;
        try {
            year = Year.parse(yearStr);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid Year format. Expected format: yyyy");
        }
        List<ResPayrollDTO> Payrolls = this.payrollService.getAllPayRollByYear(year);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get All Payrolls By Year",
                        Payrolls
                )
        );
    }

}
