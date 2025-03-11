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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @PutMapping("/scan")
    public ResponseEntity<ResponseDTO<List<ResAttendanceDetailDTO>>> scanEndOfDay(@RequestParam("dateScan") LocalDateTime dateScan) {
        List<ResAttendanceDetailDTO> resAttendanceDetailDTOS = this.attendanceDetailService.processDailyAttendance(dateScan);
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
        List<ResAttendanceDetailDTO> resAttendanceDetailDTOS = this.attendanceDetailService.handleGetAllByMonth(id);
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
    public ResponseEntity<ResponseDTO<List<ResAttendanceDetailDTO>>> getAllAttendanceDetailByUser(@RequestParam("date") LocalDate date) {
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
}
