package vn.bookstore.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.ReqPayrollDTO;
import vn.bookstore.app.dto.response.ResPayrollDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.model.Payroll;
import vn.bookstore.app.service.impl.PayrollServiceImpl;
import vn.bookstore.app.util.error.InvalidRequestException;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

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
    public ResponseEntity<ResponseDTO<List<ResPayrollDTO>>> GetAllPayrollByUserByYear(@PathVariable Long userId, @RequestParam String yearStr) {
        Year year;
        try {
            year = Year.parse(yearStr);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid Year format. Expected format: yyyy");
        }
        List<ResPayrollDTO> newPayrolls = this.payrollService.getAllPayRollByUserByYear(userId,year);
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
    public ResponseEntity<ResponseDTO> exportPayroll(@PathVariable Long id) {
        ResPayrollDTO payrollDTO = this.payrollService.getPayrollById(id);
        String filePath = "PayrollReport_" + payrollDTO.getUserIdStr() + "_" + payrollDTO.getMonthOfYear() + ".pdf";
        try {
            payrollService.generatePayrollPdf(filePath, payrollDTO);
            return ResponseEntity.ok(
                    new ResponseDTO<>(
                            200,
                            true,
                            null,
                            "Reject LeaveRequest successfully",
                            null
                    )
            );
        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }
    }

    @GetMapping("/payrolls/export/year/{userId}")
    public ResponseEntity<ResponseDTO> exportPayrollByYear(@PathVariable Long userId,@RequestParam String yearStr) {
        Year year;
        try {
            year = Year.parse(yearStr);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid Year format. Expected format: yyyy");
        }
        List<ResPayrollDTO> Payrolls = this.payrollService.getAllPayRollByUserByYear(userId,year);
        String filePath = "PayrollReport_NV-" + userId + "_" + year + ".pdf";
        try {
            payrollService.generatePayrollByYearPdf(filePath, Payrolls);
            return ResponseEntity.ok(
                    new ResponseDTO<>(
                            200,
                            true,
                            null,
                            "Reject LeaveRequest successfully",
                            null
                    )
            );
        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }
    }

}
