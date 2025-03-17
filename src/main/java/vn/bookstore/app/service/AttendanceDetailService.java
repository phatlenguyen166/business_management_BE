package vn.bookstore.app.service;

import vn.bookstore.app.dto.request.ReqAttendanceDetailDTO;
import vn.bookstore.app.dto.response.ResAttendanceDetailDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public interface AttendanceDetailService {
    public ResAttendanceDetailDTO handleCreateAttendanceDetail(ReqAttendanceDetailDTO attendanceDetail);
    public ResAttendanceDetailDTO handleCheckOut(ReqAttendanceDetailDTO attendanceDetailDTO);
    public List<ResAttendanceDetailDTO> processDailyAttendance(LocalDateTime localDateTime);
    public List<ResAttendanceDetailDTO> handleGetAll();
    public List<ResAttendanceDetailDTO> handleGetAllByAttendance(Long id);
    public List<ResAttendanceDetailDTO> handleGetAllByUser(Long id);
    public List<ResAttendanceDetailDTO> handleGetAllByDate(LocalDate date);
    public List<ResAttendanceDetailDTO> handleGetAllByMonth(YearMonth yearMonth);



}
