package vn.bookstore.app.service;

import vn.bookstore.app.dto.response.PayrollStatisticsDTO;
import vn.bookstore.app.dto.response.PayrollStatisticsViewDTO;

import java.time.Year;
import java.time.YearMonth;
import java.util.List;

public interface PayrollStatisticsService {
    PayrollStatisticsViewDTO getMonthlyStatistics(YearMonth month);
    PayrollStatisticsViewDTO getYearlyStatistics(Year year);
    List<PayrollStatisticsViewDTO> getMonthlyStatisticsByYear(Year year);

}
