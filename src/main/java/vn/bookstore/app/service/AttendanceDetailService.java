package vn.bookstore.app.service;

import vn.bookstore.app.dto.request.ReqAttendanceDetailDTO;
import vn.bookstore.app.dto.response.ResAttendanceDetailDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceDetailService {
    public ResAttendanceDetailDTO handleCreateAttendanceDetail(ReqAttendanceDetailDTO attendanceDetail);
    public ResAttendanceDetailDTO handleCheckOut(ReqAttendanceDetailDTO attendanceDetailDTO);
    public List<ResAttendanceDetailDTO> processDailyAttendance(LocalDateTime localDateTime);
    public List<ResAttendanceDetailDTO> handleGetAll();
    public List<ResAttendanceDetailDTO> handleGetAllByMonth(Long id);
}
