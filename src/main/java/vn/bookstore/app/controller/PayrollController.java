package vn.bookstore.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.model.Payroll;
import vn.bookstore.app.service.impl.PayrollServiceImpl;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PayrollController {
    private final PayrollServiceImpl payrollService;


//    @PostMapping("/payrolls/user/{userId}")
//    public ResponseEntity<ResponseDTO<Payroll>> createPayroll(@PathVariable Long userId, @RequestBody Payroll payroll) {
//        Payroll newPayroll = this.payrollService.createPayrollByUser(userId, payroll);
//    }
}
