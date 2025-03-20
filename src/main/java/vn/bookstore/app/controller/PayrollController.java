package vn.bookstore.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.ReqPayrollDTO;
import vn.bookstore.app.dto.response.ResPayrollDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.model.Payroll;
import vn.bookstore.app.service.impl.PayrollServiceImpl;
import vn.bookstore.app.util.error.InvalidRequestException;

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
                        200,
                        true,
                        null,
                        "Create Payroll successfully",
                        newPayrolls
                )
        );
    }
}
