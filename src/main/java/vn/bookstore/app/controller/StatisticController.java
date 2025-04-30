package vn.bookstore.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.bookstore.app.model.StaffStatisticOfMonth;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.impl.StaffStatisticServiceImpl;
import vn.bookstore.app.util.error.InvalidRequestException;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StatisticController {
    private final StaffStatisticServiceImpl staffStatisticService;

    @GetMapping("/staffStatistics/month")
    public ResponseEntity<ResponseDTO<StaffStatisticOfMonth>> getAllHoliday(@RequestParam("yearMonth") String yearMonthStr) {
        YearMonth yearMonth;
        try {
            yearMonth = YearMonth.parse(yearMonthStr);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid yearMonth format. Expected format: yyyy-MM");
        }
        StaffStatisticOfMonth staffStatisticDTO  = this.staffStatisticService.getStaffStatisticMonth(yearMonth);

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
}
