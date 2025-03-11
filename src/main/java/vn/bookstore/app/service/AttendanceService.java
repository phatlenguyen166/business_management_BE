package vn.bookstore.app.service;

import vn.bookstore.app.dto.response.ResAttendanceDTO;

import java.util.List;

public interface AttendanceService {
    public List<ResAttendanceDTO> handleGetAll();
    public List<ResAttendanceDTO> handleGetAllByUser(Long userId);
}
