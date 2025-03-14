package vn.bookstore.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqAttendanceDetailDTO;
import vn.bookstore.app.dto.response.ResAttendanceDetailDTO;
import vn.bookstore.app.mapper.AttendanceDetailMapper;
import vn.bookstore.app.model.*;
import vn.bookstore.app.repository.*;
import vn.bookstore.app.service.AttendanceDetailService;
import vn.bookstore.app.util.constant.AttendanceStatusEnum;
import vn.bookstore.app.util.constant.LeaveTypeEnum;
import vn.bookstore.app.util.error.InvalidRequestException;
import vn.bookstore.app.util.error.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AttendanceDetailServiceImpl implements AttendanceDetailService {
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceDetailRepository attendanceDetailRepository;
    private final AttendanceDetailMapper attendanceDetailMapper;
    private final HolidayRepository holidayRepository;
    private final LeaveReqRepository leaveReqRepository;
    private final ContractRepository contractRepository;


    @Override
    public ResAttendanceDetailDTO handleCreateAttendanceDetail(ReqAttendanceDetailDTO attendanceDetail) {
        User user = this.userRepository.findUserByIdAndStatus(attendanceDetail.getUserId(), 1).orElseThrow(() -> new NotFoundException("User Không tồn tại"));
        YearMonth yearMonth = YearMonth.from(attendanceDetail.getCheckIn());
        LocalDate date = LocalDate.from(attendanceDetail.getCheckIn());
        Attendance attendance = this.attendanceRepository.findByUserAndMonthOfYear(user, yearMonth.toString());
        AttendanceDetail currAttendanceDetail = this.attendanceDetailRepository.findByAttendanceAndCheckInDate(attendance, date);
        if (currAttendanceDetail != null && currAttendanceDetail.getCheckIn() != null) {
            throw new InvalidRequestException("Nhân viên đã check-in hôm nay, không thể check-in lại!");
        }
        if (attendance == null) {
            Attendance newAttendance = new Attendance();
            newAttendance.setUser(user);
            newAttendance.setMonthOfYear(yearMonth.toString());
            this.attendanceRepository.save(newAttendance);
            AttendanceDetail detail = new AttendanceDetail();
            detail.setAttendance(newAttendance);
            detail.setCheckIn(LocalTime.from(attendanceDetail.getCheckIn()).now());
            detail.setWorkingDay(date);
            this.attendanceDetailRepository.save(detail);
            return this.attendanceDetailMapper.convertToResAttendanceDetailDTO(detail);
        }
        AttendanceDetail detail = new AttendanceDetail();
        detail.setAttendance(attendance);
        detail.setCheckIn(LocalTime.from(attendanceDetail.getCheckIn()));
        detail.setWorkingDay(date);
        this.attendanceDetailRepository.save(detail);
        return this.attendanceDetailMapper.convertToResAttendanceDetailDTO(detail);
    }

    @Override
    public ResAttendanceDetailDTO handleCheckOut(ReqAttendanceDetailDTO attendanceDetailDTO) {
        User user = this.userRepository.findUserByIdAndStatus(attendanceDetailDTO.getUserId(), 1).orElseThrow(() -> new NotFoundException("User Không tồn tại"));
        YearMonth yearMonth = YearMonth.from(attendanceDetailDTO.getCheckOut());
        LocalDate date = LocalDate.from(attendanceDetailDTO.getCheckOut());
        Attendance attendance = this.attendanceRepository.findByUserAndMonthOfYear(user, yearMonth.toString());
        AttendanceDetail currAttendanceDetail = this.attendanceDetailRepository.findByAttendanceAndCheckInDate(attendance, date);
        currAttendanceDetail.setCheckOut(LocalTime.from(attendanceDetailDTO.getCheckOut()).now());
        this.attendanceDetailRepository.save(currAttendanceDetail);
        return this.attendanceDetailMapper.convertToResAttendanceDetailDTO(currAttendanceDetail);
    }

    public void handleCheckOutInValid(AttendanceDetail attendanceDetail) {
        if (attendanceDetail.getCheckOut() != null) {
            attendanceDetail.setAttendanceStatus(AttendanceStatusEnum.PRESENT);
            this.attendanceDetailRepository.save(attendanceDetail);
        } else {
            attendanceDetail.setAttendanceStatus(AttendanceStatusEnum.ABSENT);
            this.attendanceDetailRepository.save(attendanceDetail);
        }
    }

    public void updateTotalWorking(AttendanceDetail attendanceDetail, Attendance attendance) {
        if (attendanceDetail.getAttendanceStatus() == AttendanceStatusEnum.PRESENT) {
            attendance.setTotalWorkingDays(attendance.getTotalWorkingDays() + 1);
            this.attendanceRepository.save(attendance);
        } else if (attendanceDetail.getAttendanceStatus() == AttendanceStatusEnum.ABSENT) {
            attendance.setTotalUnpaidLeaves(attendance.getTotalUnpaidLeaves() + 1);
            this.attendanceRepository.save(attendance);
        } else if (attendanceDetail.getAttendanceStatus() == AttendanceStatusEnum.ON_LEAVE) {
            if (attendanceDetail.getLeaveTypeEnum() == LeaveTypeEnum.MATERNITY_LEAVE) {
                attendance.setTotalMaternityLeaves(attendance.getTotalMaternityLeaves() + 1);
                this.attendanceRepository.save(attendance);
            } else if (attendanceDetail.getLeaveTypeEnum() == LeaveTypeEnum.PAID_LEAVE) {
                attendance.setTotalPaidLeaves(attendance.getTotalPaidLeaves() + 1);
                this.attendanceRepository.save(attendance);
            } else if (attendanceDetail.getLeaveTypeEnum() == LeaveTypeEnum.SICK_LEAVE) {
                attendance.setTotalSickLeaves(attendance.getTotalSickLeaves() + 1);
                this.attendanceRepository.save(attendance);
            } else {
                attendance.setTotalHolidayLeaves(attendance.getTotalHolidayLeaves() + 1);
                this.attendanceRepository.save(attendance);
            }
        }
    }

    public List<User> getAllUserByActiveContract() {
        List<User> usersActive = new ArrayList<>();
        List<Contract> contracts = this.contractRepository.getAllByStatus(1);
        for (Contract contract : contracts) {
            if(this.userRepository.findUserByIdAndStatus(contract.getUser().getId(),1).isPresent()) {
                usersActive.add(contract.getUser());
            }
        }
        return  usersActive;
    }

    @Override
    public List<ResAttendanceDetailDTO> processDailyAttendance(LocalDateTime dateTime) {
        LocalDateTime scanDateTime = dateTime; // dateTime nhận từ request
        LocalDate scannedDate = scanDateTime.toLocalDate();
        LocalDate currentDate = LocalDate.now();

// Tạo thời điểm 23:00 của ngày hiện tại
        LocalDateTime todayAfter23 = currentDate.atTime(23, 0);
        if (scannedDate.isBefore(currentDate)) {
        } else if (scannedDate.equals(currentDate)) {
            if (scanDateTime.isAfter(todayAfter23) || scanDateTime.equals(todayAfter23)) {
            } else {
                throw new IllegalArgumentException("Scan cho ngày hiện tại chỉ được chấp nhận sau 23:00.");
            }
        } else {
            // Ngày scan là tương lai -> không cho phép
            throw new IllegalArgumentException("Scan cho ngày tương lai không được chấp nhận.");
        }
        LocalDate today = LocalDate.from(scanDateTime);
        List<User> users = getAllUserByActiveContract();
        for (User user : users) {
            String monthOfYear = YearMonth.from(today).toString();
            Attendance attendance = attendanceRepository.findByUserAndMonthOfYear(user, monthOfYear);
            if (attendance == null) {
                attendance = new Attendance();
                attendance.setUser(user);
                attendance.setMonthOfYear(monthOfYear);
                attendance = attendanceRepository.save(attendance);
            }

            AttendanceDetail optionalAttendanceDetail = attendanceDetailRepository.findByAttendanceAndCheckInDate(attendance, today);

            Optional<LeaveRequest> leaveRequestOpt = leaveReqRepository.findLeaveRequestByUserAndDateAndStatus(user, today,1);
            Optional<Holiday> holidayOpt = holidayRepository.findHolidayByDate(today);
            if (optionalAttendanceDetail != null) {
                AttendanceDetail attendanceDetail = optionalAttendanceDetail;
                if (leaveRequestOpt.isPresent()) {
                    attendanceDetail.setAttendanceStatus(AttendanceStatusEnum.ON_LEAVE);
                    attendanceDetail.setLeaveTypeEnum(leaveRequestOpt.get().getLeaveReason());
                } else if (holidayOpt.isPresent()) {
                    attendanceDetail.setAttendanceStatus(AttendanceStatusEnum.ON_LEAVE);
                    attendanceDetail.setLeaveTypeEnum(LeaveTypeEnum.HOLIDAY);
                    attendanceDetail.setHoliday(holidayOpt.get());
                } else {
                    handleCheckOutInValid(attendanceDetail);
                }
                attendanceDetailRepository.save(attendanceDetail);
                updateTotalWorking(attendanceDetail, attendance);
            } else {
                AttendanceDetail newDetail = new AttendanceDetail();
                newDetail.setAttendance(attendance);
                newDetail.setWorkingDay(today);
                if (leaveRequestOpt.isPresent()) {
                    newDetail.setAttendanceStatus(AttendanceStatusEnum.ON_LEAVE);
                    newDetail.setLeaveTypeEnum(leaveRequestOpt.get().getLeaveReason());
                } else if (holidayOpt.isPresent()) {
                    newDetail.setAttendanceStatus(AttendanceStatusEnum.ON_LEAVE);
                    newDetail.setLeaveTypeEnum(LeaveTypeEnum.HOLIDAY);
                    newDetail.setHoliday(holidayOpt.get());
                } else {
                    newDetail.setAttendanceStatus(AttendanceStatusEnum.ABSENT);
                }
                newDetail = attendanceDetailRepository.save(newDetail);
                updateTotalWorking(newDetail, attendance);
            }
        }
        return handleGetAll();
    }


    @Override
    public List<ResAttendanceDetailDTO> handleGetAll() {
        List<ResAttendanceDetailDTO> resAttendanceDetailDTOS = this.attendanceDetailRepository.findAll()
                .stream()
                .map(attendanceDetailMapper::convertToResAttendanceDetailDTO)
                .collect(Collectors.toList());
        return resAttendanceDetailDTOS;
    }

    @Override
    public List<ResAttendanceDetailDTO> handleGetAllByMonth(Long id) {
        Attendance attendance = this.attendanceRepository.findById(id).orElseThrow(() -> new NotFoundException("Attendance not found"));
        List<ResAttendanceDetailDTO> attendanceDetailDTOS = this.attendanceDetailRepository.findAllByAttendance(attendance).stream()
                .map(attendanceDetailMapper::convertToResAttendanceDetailDTO)
                .collect(Collectors.toList());
        return attendanceDetailDTOS;
    }

    @Override
    public List<ResAttendanceDetailDTO> handleGetAllByUser(Long id) {
      User user = this.userRepository.findUserByIdAndStatus(id,1).orElseThrow(() -> new NotFoundException("User not found"));
       List<Attendance> attendances = this.attendanceRepository.findAllByUserOrderByMonthOfYearDesc(user);
       List<AttendanceDetail> attendanceDetails = new ArrayList<>();
       for (Attendance attendance : attendances) {
           List<AttendanceDetail> details = attendanceDetailRepository.findAllByAttendance(attendance);
           attendanceDetails.addAll(details);
       }
       return attendanceDetails.stream().
               map(attendanceDetailMapper::convertToResAttendanceDetailDTO)
               .collect(Collectors.toList());
    }

    @Override
    public List<ResAttendanceDetailDTO> handleGetAllByDate(LocalDate date) {
        List<ResAttendanceDetailDTO> attendanceDetailDTOS = this.attendanceDetailRepository.findAllByWorkingDay(date).stream()
                .map(attendanceDetailMapper::convertToResAttendanceDetailDTO)
                .collect(Collectors.toList());
        return attendanceDetailDTOS;
    }
}
