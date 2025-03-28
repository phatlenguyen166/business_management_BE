package vn.bookstore.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.ReqAttendanceDetailDTO;
import vn.bookstore.app.dto.response.ResAttendanceDetailDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.impl.AttendanceDetailServiceImpl;
import vn.bookstore.app.util.error.InvalidRequestException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AttendanceDetailController {
    private final AttendanceDetailServiceImpl attendanceDetailService;


    @PostMapping("/checkIn")
    public ResponseEntity<ResponseDTO<ResAttendanceDetailDTO>> checkIn(@Valid @RequestBody ReqAttendanceDetailDTO attendanceDetail) {
      ResAttendanceDetailDTO attendanceDetailDTO = this.attendanceDetailService.handleCreateAttendanceDetail(attendanceDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO<>(
                        201,
                        true,
                        null,
                        "Check in successfully",
                        attendanceDetailDTO
                )
        );
    }
    @PutMapping("/checkOut")
    public ResponseEntity<ResponseDTO<ResAttendanceDetailDTO>> checkOut(@Valid @RequestBody ReqAttendanceDetailDTO attendanceDetail) {
        ResAttendanceDetailDTO attendanceDetailDTO = this.attendanceDetailService.handleCheckOut(attendanceDetail);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Check out successfully",
                        attendanceDetailDTO
                )
        );
    }

    @PutMapping("/attendanceDetails/update")
    public ResponseEntity<ResponseDTO<ResAttendanceDetailDTO>> update(@Valid @RequestBody ReqAttendanceDetailDTO attendanceDetail) {
        ResAttendanceDetailDTO attendanceDetailDTO = this.attendanceDetailService.handleUpdate(attendanceDetail);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Update successfully",
                        attendanceDetailDTO
                )
        );
    }

    @PutMapping("/scan")
    public ResponseEntity<ResponseDTO> scanEndOfDay(@RequestParam("dateScan") LocalDateTime dateScan) {
         this.attendanceDetailService.processDailyAttendance(dateScan);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Scan successfully",
                        null
                )
        );
    }

    @GetMapping("/attendanceDetails")
    public ResponseEntity<ResponseDTO<List<ResAttendanceDetailDTO>>> getAllAttendanceDetail() {
        List<ResAttendanceDetailDTO> resAttendanceDetailDTOS = this.attendanceDetailService.handleGetAll();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Scan successfully",
                        resAttendanceDetailDTOS
                )
        );
    }

    @GetMapping("/attendanceDetails/attendance/{id}")
    public ResponseEntity<ResponseDTO<List<ResAttendanceDetailDTO>>> getAllAttendanceDetailByMonth(@PathVariable Long id) {
        List<ResAttendanceDetailDTO> resAttendanceDetailDTOS = this.attendanceDetailService.handleGetAllByAttendance(id);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get AttendanceDetails by attendance successfully",
                        resAttendanceDetailDTOS
                )
        );
    }

    @GetMapping("/attendanceDetails/user/{userId}")
    public ResponseEntity<ResponseDTO<List<ResAttendanceDetailDTO>>> getAllAttendanceDetailByUser(@PathVariable Long userId) {
        List<ResAttendanceDetailDTO> resAttendanceDetailDTOS = this.attendanceDetailService.handleGetAllByUser(userId);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get AttendanceDetails By User successfully",
                        resAttendanceDetailDTOS
                )
        );
    }

    @GetMapping("/attendanceDetails/date")
    public ResponseEntity<ResponseDTO<List<ResAttendanceDetailDTO>>> getAllAttendanceDetailByDate(@RequestParam("date") String dateString) {
        LocalDate date;
        try {
             date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid yearMonth format. Expected format: yyyy-MM-dd");
        }
        List<ResAttendanceDetailDTO> resAttendanceDetailDTOS = this.attendanceDetailService.handleGetAllByDate(date);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all attendanceDetails By LocalDate successfully",
                        resAttendanceDetailDTOS
                )
        );
    }

    @GetMapping("/attendanceDetails/month")
    public ResponseEntity<ResponseDTO<List<ResAttendanceDetailDTO>>> getAllAttendanceDetailByMonth(@RequestParam("yearMonth") String yearMonthStr) {
        YearMonth yearMonth;
        try {
            yearMonth = YearMonth.parse(yearMonthStr);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid yearMonth format. Expected format: yyyy-MM");
        }
        List<ResAttendanceDetailDTO> resAttendanceDetailDTOS = this.attendanceDetailService.handleGetAllByMonth(yearMonth);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all attendanceDetails By LocalDate successfully",
                        resAttendanceDetailDTOS
                )
        );
    }

    @GetMapping("/attendanceDetails/checkExist/{userId}")
    public ResponseEntity<ResponseDTO<String>> checkExistAttendanceDetail(@RequestParam("date") String dateString, @PathVariable Long userId) {
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid yearMonth format. Expected format: yyyy-MM-dd");
        }
        String result = this.attendanceDetailService.checkExistAttendanceDetail(date, userId);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all attendanceDetails By LocalDate successfully",
                        result
                )
        );
    }
}
