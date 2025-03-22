package vn.bookstore.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.response.ResAttendanceDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.impl.AttendanceServiceImpl;
import vn.bookstore.app.util.error.InvalidRequestException;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceServiceImpl attendanceService;

    @GetMapping("/attendances")
    public ResponseEntity<ResponseDTO<List<ResAttendanceDTO>>> getAllAttendance() {
        List<ResAttendanceDTO> resAttendanceDTOS = this.attendanceService.handleGetAll();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all attendances successfully",
                        resAttendanceDTOS
                )
        );
    }

    @GetMapping("/attendances/user/{userId}")
    public ResponseEntity<ResponseDTO<List<ResAttendanceDTO>>> getAllAttendanceByUser(@PathVariable Long userId) {
        List<ResAttendanceDTO> resAttendanceDTOS = this.attendanceService.handleGetAllByUser(userId);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get attendance By User successfully",
                        resAttendanceDTOS
                )
        );
    }

    @GetMapping("/attendances/yearMonth")
    public ResponseEntity<ResponseDTO<List<ResAttendanceDTO>>> getAllAttendanceByMonth(@RequestParam("yearMonth") String yearMonthStr) {
        YearMonth yearMonth;
        try {
            yearMonth = YearMonth.parse(yearMonthStr);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid yearMonth format. Expected format: yyyy-MM");
        }
        List<ResAttendanceDTO> resAttendanceDTOS = this.attendanceService.handleGetAllByMonth(yearMonth);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all attendanceDetails By LocalDate successfully",
                        resAttendanceDTOS
                )
        );
    }
}
