package vn.bookstore.app.service;

import java.io.IOException;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

import vn.bookstore.app.dto.response.ResPayrollDTO;

public interface PayrollService {

    List<ResPayrollDTO> createPayroll(YearMonth yearMonth);

    List<ResPayrollDTO> getAllPayRoll();

    List<ResPayrollDTO> getAllPayRollByUser(Long userId);

    List<ResPayrollDTO> getAllPayRollByUserByYear(Long userId, Year year);

    List<ResPayrollDTO> getAllPayRollByMonth(YearMonth yearMonth);

    List<ResPayrollDTO> getAllPayRollByYear(Year year);

    ResPayrollDTO getPayrollById(Long id);

    String generatePayrollPdf(String filePath, ResPayrollDTO payroll) throws IOException;

    String generatePayrollByYearPdf(String filePath, List<ResPayrollDTO> Payrolls) throws IOException;

}
