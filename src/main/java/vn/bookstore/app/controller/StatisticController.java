package vn.bookstore.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.bookstore.app.dto.response.PayrollStatisticsDTO;
import vn.bookstore.app.dto.response.PayrollStatisticsViewDTO;
import vn.bookstore.app.model.StaffStatisticMonth;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.model.StaffStatisticYear;
import vn.bookstore.app.service.PayrollStatisticsService;
import vn.bookstore.app.service.impl.StaffStatisticMonthServiceImpl;
import vn.bookstore.app.service.impl.StaffStatisticYearServiceImpl;
import vn.bookstore.app.util.error.InvalidRequestException;

import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StatisticController {
    private final StaffStatisticMonthServiceImpl statisticMonthService;
    private final StaffStatisticYearServiceImpl statisticYearService;
    private final PayrollStatisticsService statsService;

    @GetMapping("/staffStatistics/month")
    public ResponseEntity<ResponseDTO<StaffStatisticMonth>> staffStatisticMonth(@RequestParam("yearMonth") String yearMonthStr) {
        YearMonth yearMonth;
        try {
            yearMonth = YearMonth.parse(yearMonthStr);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid yearMonth format. Expected format: yyyy-MM");
        }
        StaffStatisticMonth staffStatisticDTO  = this.statisticMonthService.getStaffStatisticMonth(yearMonth);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all holidays successfully",
                        staffStatisticDTO
                )
        );
    }

    @GetMapping("/staffStatistics/year")
    public ResponseEntity<ResponseDTO<StaffStatisticYear>> staffStatisticYear(@RequestParam("year") String yearStr) {
        Year year;
        try {
            year = Year.parse(yearStr);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid yearMonth format. Expected format: yyyy");
        }
        StaffStatisticYear staffStatisticYear = this.statisticYearService.getStaffStatisticYear(year);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all holidays successfully",
                        staffStatisticYear
                )
        );
    }

    @GetMapping("/salaryStatistics/month")
    public ResponseEntity<PayrollStatisticsViewDTO> month(@RequestParam String month) {
        YearMonth ym = YearMonth.parse(month);     // "2025-03"
        return ResponseEntity.ok(statsService.getMonthlyStatistics(ym));
    }

    @GetMapping("/salaryStatistics/year")
    public ResponseEntity<PayrollStatisticsViewDTO> year(@RequestParam String year) {
        Year y = Year.parse(year);                // "2025"
        return ResponseEntity.ok(statsService.getYearlyStatistics(y));
    }
}
