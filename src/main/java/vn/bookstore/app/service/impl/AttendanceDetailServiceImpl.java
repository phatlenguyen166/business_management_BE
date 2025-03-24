package vn.bookstore.app.service.impl;

import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqAttendanceDetailDTO;
import vn.bookstore.app.dto.response.ResAttendanceDetailDTO;
import vn.bookstore.app.mapper.AttendanceDetailMapper;
import vn.bookstore.app.model.*;
import vn.bookstore.app.repository.*;
import vn.bookstore.app.service.AttendanceDetailService;
import vn.bookstore.app.util.constant.AttendanceStatusEnum;
import vn.bookstore.app.util.constant.LateTypeEnum;
import vn.bookstore.app.util.constant.LeaveTypeEnum;
import vn.bookstore.app.util.error.InvalidRequestException;
import vn.bookstore.app.util.error.NotFoundException;

import java.time.*;
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



    public boolean isHoliday(LocalDate date, List<Holiday> holidays) {
        for (Holiday holiday : holidays) {
            if (!date.isBefore(holiday.getStartDate()) && !date.isAfter(holiday.getEndDate())) {
                return true;
            }
        }
        return false;
    }

    public int getStandardWorkingDays(YearMonth yearMonth) {
        int workingDays = 0;
        List<Holiday> holidays = holidayRepository.findAllByStatus(1);
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = yearMonth.atDay(day);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY && !isHoliday(date, holidays)) {
                workingDays++;
            }
        }
        System.out.println(workingDays);
        return workingDays;

    }

    public void handleCheckIn(AttendanceDetail attendanceDetail) {
        if (attendanceDetail.getCheckIn().isAfter(LocalTime.of(9,0)) && attendanceDetail.getCheckIn().isBefore(LocalTime.of(10,0))) {
            attendanceDetail.setLateTypeEnum(LateTypeEnum.LATE_1);
        } else if (attendanceDetail.getCheckIn().isAfter(LocalTime.of(10,0)) && attendanceDetail.getCheckIn().isBefore(LocalTime.of(12,0))) {
            attendanceDetail.setLateTypeEnum(LateTypeEnum.LATE_2);
        } else if (attendanceDetail.getCheckIn().isAfter(LocalTime.of(13,30)) && attendanceDetail.getCheckIn().isBefore(LocalTime.of(14,0))) {
            attendanceDetail.setLateTypeEnum(LateTypeEnum.LATE_3);
        } else if (attendanceDetail.getCheckIn().isAfter(LocalTime.of(14,0))) {
            attendanceDetail.setLateTypeEnum(LateTypeEnum.LATE_4);
        } else if (attendanceDetail.getCheckIn().isBefore(LocalTime.of(9,0))) {
            attendanceDetail.setLateTypeEnum(null);
        }
        attendanceDetailRepository.save(attendanceDetail);
    }

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
            newAttendance.setStandardWorkingDays(getStandardWorkingDays(YearMonth.from(attendanceDetail.getCheckIn())));
            newAttendance.setUser(user);
            newAttendance.setMonthOfYear(yearMonth.toString());
            this.attendanceRepository.save(newAttendance);
            AttendanceDetail detail = new AttendanceDetail();
            detail.setAttendance(newAttendance);
            detail.setCheckIn(attendanceDetail.getCheckIn().toLocalTime());
            detail.setWorkingDay(date);
            this.attendanceDetailRepository.save(detail);
            return this.attendanceDetailMapper.convertToResAttendanceDetailDTO(detail);
        }
        AttendanceDetail detail = new AttendanceDetail();
        detail.setAttendance(attendance);
        detail.setCheckIn(LocalTime.from(attendanceDetail.getCheckIn()));
        detail.setWorkingDay(date);
        this.attendanceDetailRepository.save(detail);
        ResAttendanceDetailDTO test = this.attendanceDetailMapper.convertToResAttendanceDetailDTO(detail);
        return test;
    }

    @Override
    public ResAttendanceDetailDTO handleCheckOut(ReqAttendanceDetailDTO attendanceDetailDTO) {
        User user = this.userRepository.findUserByIdAndStatus(attendanceDetailDTO.getUserId(), 1).orElseThrow(() -> new NotFoundException("User Không tồn tại"));
        YearMonth yearMonth = YearMonth.from(attendanceDetailDTO.getCheckOut());
        LocalDate date = LocalDate.from(attendanceDetailDTO.getCheckOut());
        Attendance attendance = this.attendanceRepository.findByUserAndMonthOfYear(user, yearMonth.toString());
        AttendanceDetail currAttendanceDetail = this.attendanceDetailRepository.findByAttendanceAndCheckInDate(attendance, date);
        currAttendanceDetail.setCheckOut(attendanceDetailDTO.getCheckOut().toLocalTime());
        this.attendanceDetailRepository.save(currAttendanceDetail);
        return this.attendanceDetailMapper.convertToResAttendanceDetailDTO(currAttendanceDetail);
    }

    @Override
    public ResAttendanceDetailDTO handleUpdate(ReqAttendanceDetailDTO attendanceDetailDTO) {
        User user = this.userRepository.findUserByIdAndStatus(attendanceDetailDTO.getUserId(), 1).orElseThrow(() -> new NotFoundException("User Không tồn tại"));
        YearMonth yearMonth = YearMonth.from(attendanceDetailDTO.getCheckOut());
        LocalDate date = LocalDate.from(attendanceDetailDTO.getCheckOut());
        Attendance attendance = this.attendanceRepository.findByUserAndMonthOfYear(user, yearMonth.toString());
        AttendanceDetail currAttendanceDetail = this.attendanceDetailRepository.findByAttendanceAndCheckInDate(attendance, date);
        if (attendanceDetailDTO.getCheckIn() != null) {
           currAttendanceDetail.setCheckIn(attendanceDetailDTO.getCheckIn().toLocalTime());
        }
        if (attendanceDetailDTO.getCheckOut() != null) {
            currAttendanceDetail.setCheckOut(attendanceDetailDTO.getCheckOut().toLocalTime());
        }
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
        LocalDateTime scanDateTime = dateTime;
        LocalDate scannedDate = scanDateTime.toLocalDate();
        LocalDate currentDate = LocalDate.now();

        if (scannedDate.isAfter(currentDate)) {
            throw new IllegalArgumentException("Scan cho ngày tương lai không được chấp nhận.");
        }
        LocalDate today = LocalDate.from(scanDateTime);
        List<User> users = getAllUserByActiveContract();
        for (User user : users) {
            String monthOfYear = YearMonth.from(today).toString();
            Attendance attendance = attendanceRepository.findByUserAndMonthOfYear(user, monthOfYear);
            if (attendance == null) {
                attendance = new Attendance();
                attendance.setStandardWorkingDays(getStandardWorkingDays(YearMonth.from(scannedDate)));
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
                if (attendanceDetail.getCheckIn() != null) {
                    handleCheckIn(attendanceDetail);
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
    public List<ResAttendanceDetailDTO> handleGetAllByAttendance(Long id) {
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

    @Override
    public List<ResAttendanceDetailDTO> handleGetAllByMonth(YearMonth yearMonth) {
        int year = yearMonth.getYear();
        int month = yearMonth.getMonthValue();
        List<ResAttendanceDetailDTO> attendanceDetailDTOS = this.attendanceDetailRepository.findAllByMonth(year, month).stream()
                .map(attendanceDetailMapper::convertToResAttendanceDetailDTO)
                .collect(Collectors.toList());
        return attendanceDetailDTOS;
    }
}
