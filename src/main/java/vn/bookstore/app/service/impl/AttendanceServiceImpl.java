package vn.bookstore.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.response.ResAttendanceDTO;
import vn.bookstore.app.mapper.AttendanceMapper;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.AttendanceRepository;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.AttendanceService;
import vn.bookstore.app.util.error.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final UserRepository userRepository;

    @Override
    public List<ResAttendanceDTO> handleGetAll() {
        List<ResAttendanceDTO> resAttendanceDTOS = this.attendanceRepository.findAllByOrderByMonthOfYearDesc().stream()
                .map((attendanceMapper::convertToResAttendanceDTO))
                .collect(Collectors.toList());
        return resAttendanceDTOS;
    }

    @Override
    public List<ResAttendanceDTO> handleGetAllByUser(Long userId) {
        User user = this.userRepository.findUserByIdAndStatus(userId,1).orElseThrow(() -> new NotFoundException("User not found"));
        List<ResAttendanceDTO> resAttendanceDTOS = this.attendanceRepository.findAllByUserOrderByMonthOfYearDesc(user).stream()
                .map(attendanceMapper::convertToResAttendanceDTO)
                .collect(Collectors.toList());
        return resAttendanceDTOS;

    }
}
