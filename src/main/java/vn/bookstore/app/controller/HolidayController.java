package vn.bookstore.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.response.ResHolidayDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.model.Holiday;
import vn.bookstore.app.service.impl.HolidayServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HolidayController {
    private final HolidayServiceImpl holidayService;

    @PostMapping("/holidays")
    public ResponseEntity<ResponseDTO<ResHolidayDTO>> createHoliday(@Valid @RequestBody Holiday holiday) {
        ResHolidayDTO newHoliday = this.holidayService.handleCreateHoliday(holiday);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO<>(
                        201,
                        true,
                        null,
                        "Create holiday successfully",
                        newHoliday
                )
        );
    }

    @GetMapping("/holidays")
    public ResponseEntity<ResponseDTO<List<ResHolidayDTO>>> getAllHoliday() {
        List<ResHolidayDTO> holidays  = this.holidayService.handleGetAllHoliday();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all holidays successfully",
                        holidays
                )
        );
    }

    @GetMapping("/holidays/{id}")
    public ResponseEntity<ResponseDTO<ResHolidayDTO>> getHolidayById(@PathVariable Long id) {
        ResHolidayDTO holiday  = this.holidayService.handleGetHolidayById(id);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get holiday successfully",
                        holiday
                )
        );
    }

    @PutMapping("/holidays/{id}")
    public ResponseEntity<ResponseDTO<ResHolidayDTO>> updateHoliday(@Valid @RequestBody Holiday holiday, @PathVariable Long id) {
        ResHolidayDTO updateHoliday = this.holidayService.handleUpdateHoliday(holiday,id);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Update holiday successfully",
                        updateHoliday
                )
        );
    }

    @PatchMapping("/holidays/{id}")
    public ResponseEntity<ResponseDTO> deleteHoliday( @PathVariable Long id) {
         this.holidayService.handleDeleteHoliday(id);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Delete holiday successfully",
                       null
                )
        );
    }



}
