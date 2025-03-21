package vn.bookstore.app.service;

import vn.bookstore.app.dto.response.ResPayrollDTO;
import vn.bookstore.app.model.Payroll;
import vn.bookstore.app.model.User;

import java.io.IOException;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

public interface PayrollService {
    List<ResPayrollDTO> createPayroll(YearMonth yearMonth);
    List<ResPayrollDTO> getAllPayRoll();
    List<ResPayrollDTO> getAllPayRollByUser(Long userId);
    List<ResPayrollDTO> getAllPayRollByUserByYear(Long userId, Year year);
    ResPayrollDTO getPayrollById(Long id);
    void generatePayrollPdf(String filePath, ResPayrollDTO payroll) throws IOException;

}
