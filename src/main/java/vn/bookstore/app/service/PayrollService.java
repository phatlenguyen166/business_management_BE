package vn.bookstore.app.service;

import vn.bookstore.app.dto.response.ResPayrollDTO;

import java.time.YearMonth;
import java.util.List;

public interface PayrollService {
    List<ResPayrollDTO> createPayroll(YearMonth yearMonth);
}
