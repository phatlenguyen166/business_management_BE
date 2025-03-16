package vn.bookstore.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqLeaveReqDTO;
import vn.bookstore.app.dto.response.ResLeaveReqDTO;
import vn.bookstore.app.mapper.LeaveReqMapper;
import vn.bookstore.app.model.LeaveRequest;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.LeaveReqRepository;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.LeaveReqService;
import vn.bookstore.app.util.constant.LeaveTypeEnum;
import vn.bookstore.app.util.error.InvalidRequestException;
import vn.bookstore.app.util.error.NotFoundException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveReqServiceImpl implements LeaveReqService {
    private final LeaveReqMapper leaveReqMapper;
    private final LeaveReqRepository leaveReqRepository;
    private final UserRepository userRepository;

    public long calculateLeaveDays(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date và end date không được null");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date không được trước start date");
        }
        return ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    @Override
    public List<ResLeaveReqDTO> handleCreateLeaveReq(ReqLeaveReqDTO leaveReqDTO) {
        List<ResLeaveReqDTO> resLeaveReqDTOS = new ArrayList<>();
        User user = this.userRepository.findUserByIdAndStatus(leaveReqDTO.getUserId(), 1).orElseThrow(() -> new NotFoundException("User Không tồn tại"));
        if (leaveReqDTO.getStartDate().getYear() != leaveReqDTO.getEndDate().getYear()) {
            LocalDate startDate1 = leaveReqDTO.getStartDate();
            LocalDate endDate1 = LocalDate.of(leaveReqDTO.getStartDate().getYear(), 12, 31);
            LocalDate startDate2 = LocalDate.of(leaveReqDTO.getEndDate().getYear(), 1, 1);
            LocalDate endDate2 = leaveReqDTO.getEndDate();
            LeaveRequest newLeaveReq1 = this.leaveReqMapper.convertToLeaveRequest(leaveReqDTO);
            newLeaveReq1.setStartDate(startDate1);
            newLeaveReq1.setEndDate(endDate1);
            LeaveRequest newLeaveReq2 = this.leaveReqMapper.convertToLeaveRequest(leaveReqDTO);
            newLeaveReq2.setStartDate(startDate2);
            newLeaveReq2.setEndDate(endDate2);
            if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.SICK_LEAVE) {
                List<LeaveRequest> leaveRequestExists = this.leaveReqRepository.findByUserAndStatusAndYear(user, leaveReqDTO.getStartDate(), LeaveTypeEnum.SICK_LEAVE);
                if (calculateLeaveDays(startDate1, endDate1) > 30) {
                    throw new InvalidRequestException("Nghỉ phép ốm chỉ được tối đa 30 ngày 1 năm");
                }
                if (!leaveRequestExists.isEmpty()) {
                    long totalDay = leaveRequestExists.stream().mapToLong(lr -> calculateLeaveDays(lr.getStartDate(), lr.getEndDate())).sum();
                    if ((totalDay + calculateLeaveDays(startDate1, endDate1)) > 30) {
                        throw new InvalidRequestException("Nghỉ phép ốm chỉ được tối đa 30 ngày 1 năm");
                    } else if ((totalDay + calculateLeaveDays(startDate1, endDate1)) > 30) {
                        throw new InvalidRequestException("Nghỉ phép ốm chỉ được tối đa 30 ngày 1 năm");
                    } else {
                        newLeaveReq1.setTotalDayLeave(calculateLeaveDays(startDate1, endDate1));
                        newLeaveReq1.setStatus(2);
                        newLeaveReq1.setUser(user);
                        this.leaveReqRepository.save(newLeaveReq1);
                        resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq1));
                        newLeaveReq2.setTotalDayLeave(calculateLeaveDays(startDate2, endDate2));
                        newLeaveReq2.setStatus(2);
                        newLeaveReq2.setUser(user);
                        this.leaveReqRepository.save(newLeaveReq2);
                        resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq2));
                        return resLeaveReqDTOS;
                    }
                }
            } else if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.MATERNITY_LEAVE) {
                List<LeaveRequest> leaveRequestExists = this.leaveReqRepository.findByUserAndStatusAndYear(user, leaveReqDTO.getStartDate(), LeaveTypeEnum.MATERNITY_LEAVE);
                if (calculateLeaveDays(startDate1, endDate1) > 180) {
                    throw new InvalidRequestException("Nghỉ phép thai sản chỉ được tối đa 180 ngày 1 năm");
                }
                if (!leaveRequestExists.isEmpty()) {
                    long totalDay = leaveRequestExists.stream().mapToLong(lr -> calculateLeaveDays(lr.getStartDate(), lr.getEndDate())).sum();
                    if ((totalDay + calculateLeaveDays(startDate1, endDate1)) > 180) {
                        throw new InvalidRequestException("Nghỉ phép thai sản chỉ được tối đa 180 ngày 1 năm");
                    } else if ((totalDay + calculateLeaveDays(startDate1, endDate1)) > 180) {
                        throw new InvalidRequestException("Nghỉ phép thai sản chỉ được tối đa 180 ngày 1 năm");
                    } else {
                        newLeaveReq1.setTotalDayLeave(calculateLeaveDays(startDate1, endDate1));
                        newLeaveReq1.setStatus(2);
                        newLeaveReq1.setUser(user);
                        this.leaveReqRepository.save(newLeaveReq1);
                        resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq1));
                        newLeaveReq2.setTotalDayLeave(calculateLeaveDays(startDate2, endDate2));
                        newLeaveReq2.setStatus(2);
                        newLeaveReq2.setUser(user);
                        this.leaveReqRepository.save(newLeaveReq2);
                        resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq2));
                        return resLeaveReqDTOS;
                    }
                }
            }
        }
            LeaveRequest newLeaveReq = this.leaveReqMapper.convertToLeaveRequest(leaveReqDTO);
            if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.PAID_LEAVE) {
                if (leaveReqDTO.getEndDate().isAfter(leaveReqDTO.getStartDate())) {
                    throw new InvalidRequestException("Nghỉ phép có lương chỉ được tối đa 1 ngày");
                } else if (this.leaveReqRepository.findByUserAndStatusAndMonth(user, leaveReqDTO.getStartDate(), LeaveTypeEnum.PAID_LEAVE) != null) {
                    throw new InvalidRequestException("Đã hết ngày nghỉ phép tháng này");
                }
            } else if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.SICK_LEAVE) {
                List<LeaveRequest> leaveRequestExists = this.leaveReqRepository.findByUserAndStatusAndYear(user, leaveReqDTO.getStartDate(), LeaveTypeEnum.SICK_LEAVE);
                if (calculateLeaveDays(leaveReqDTO.getStartDate(),leaveReqDTO.getEndDate()) > 30) {
                    throw new InvalidRequestException("Nghỉ phép ốm chỉ được tối đa 30 ngày 1 năm");
                } else if (!leaveRequestExists.isEmpty()) {
                    long totalDay = leaveRequestExists.stream().mapToLong(lr -> calculateLeaveDays(lr.getStartDate(), lr.getEndDate())).sum();
                    if ((totalDay + calculateLeaveDays(leaveReqDTO.getStartDate(),leaveReqDTO.getEndDate())) > 30) {
                        throw new InvalidRequestException("Nghỉ phép ốm chỉ được tối đa 30 ngày 1 năm");
                    }
                }
            } else if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.MATERNITY_LEAVE) {
                List<LeaveRequest> leaveRequestExists = this.leaveReqRepository.findByUserAndStatusAndYear(user, leaveReqDTO.getStartDate(), LeaveTypeEnum.MATERNITY_LEAVE);
                if (calculateLeaveDays(leaveReqDTO.getStartDate(),leaveReqDTO.getEndDate()) > 180) {
                    throw new InvalidRequestException("Nghỉ phép thai sản chỉ được tối đa 180 ngày 1 năm");
                } else if (!leaveRequestExists.isEmpty()) {
                    long totalDay = leaveRequestExists.stream().mapToLong(lr -> calculateLeaveDays(lr.getStartDate(), lr.getEndDate())).sum();
                    if ((totalDay + calculateLeaveDays(leaveReqDTO.getStartDate(),leaveReqDTO.getEndDate())) > 180) {
                        throw new InvalidRequestException("Nghỉ phép thai sản chỉ được tối đa 180 ngày 1 năm");
                    }
                }
            }
         newLeaveReq.setTotalDayLeave(calculateLeaveDays(leaveReqDTO.getStartDate(), leaveReqDTO.getEndDate()));
            newLeaveReq.setStatus(2);
            newLeaveReq.setUser(user);
            this.leaveReqRepository.save(newLeaveReq);
            resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq));
            return resLeaveReqDTOS;
        }





    @Override
    public List<ResLeaveReqDTO> handleGetAllLeaveReq() {
        List<ResLeaveReqDTO> resLeaveReqDTOList = this.leaveReqRepository.findByStatusInOrderBySendDateDesc(List.of(1, 2, 3)).stream().map(leaveReqMapper::convertToResLeaveReqDTO).collect(Collectors.toList());
        return resLeaveReqDTOList;
    }


    @Override
    public ResLeaveReqDTO handleGetLeaveReq(Long id) {
        return this.leaveReqMapper.convertToResLeaveReqDTO(getLeaveReqById(id));
    }

    @Override
    public LeaveRequest getLeaveReqById(Long id) {
        return this.leaveReqRepository.findByIdAndStatusIn(id, List.of(1, 2, 3)).orElseThrow(() -> new NotFoundException("Đơn xin nghỉ phép không tồn tại"));
    }

    @Override
    public ResLeaveReqDTO handleUpdateLeaveReq(Long id, ReqLeaveReqDTO leaveRequest) {
        LeaveRequest currentLeaveReq = getLeaveReqById(id);
        if (leaveRequest.getLeaveReason() == LeaveTypeEnum.PAID_LEAVE) {
            if (leaveRequest.getEndDate().isAfter(leaveRequest.getStartDate())) {
                throw new InvalidRequestException("Nghỉ phép có lương chỉ được tối đa 1 ngày");
            } else if (this.leaveReqRepository.findByUserAndStatusAndMonth(currentLeaveReq.getUser(), leaveRequest.getStartDate(), LeaveTypeEnum.PAID_LEAVE) != null) {
                throw new InvalidRequestException("Đã hết ngày nghỉ phép tháng này");
            }
        }
        this.leaveReqMapper.updateLeaveReq(leaveRequest, currentLeaveReq);
        this.leaveReqRepository.save(currentLeaveReq);
        return this.leaveReqMapper.convertToResLeaveReqDTO(currentLeaveReq);
    }

    @Override
    public void handleApproveLeaveReq(Long id) {
        LeaveRequest currentLeaveReq = this.leaveReqRepository.findByIdAndStatus(id, 2).orElseThrow(() -> new InvalidRequestException("Đơn nghỉ phép không tồn tại hoặc đã được xử lý"));
        if (this.leaveReqRepository.findByUserAndStatusAndMonth(currentLeaveReq.getUser(), currentLeaveReq.getStartDate(), LeaveTypeEnum.PAID_LEAVE) != null) {
            throw new InvalidRequestException("Đã hết ngày nghỉ phép tháng này");
        }
        currentLeaveReq.setStatus(1);
        this.leaveReqRepository.save(currentLeaveReq);
    }

    @Override
    public void handleDeleteLeaveReq(Long id) {
        LeaveRequest currentLeaveReq = this.leaveReqRepository.findByIdAndStatus(id, 2).orElseThrow(() -> new NotFoundException("Đơn xin nghỉ phép không tồn tại hoặc đã được xử lý"));
        currentLeaveReq.setStatus(0);
        this.leaveReqRepository.save(currentLeaveReq);
    }

    @Override
    public void handleRejectLeaveReq(Long id) {
        LeaveRequest currentLeaveReq = this.leaveReqRepository.findByIdAndStatus(id, 2).orElseThrow(() -> new NotFoundException("Đơn xin nghỉ phép không tồn tại hoặc đã được xử lý"));
        currentLeaveReq.setStatus(3);
        this.leaveReqRepository.save(currentLeaveReq);
    }

    @Override
    public List<ResLeaveReqDTO> handleGetAllLeaveReqByUserId(Long userId) {
        User user = this.userRepository.findUserByIdAndStatus(userId, 1).orElseThrow(() -> new NotFoundException("User Không tồn tại"));
        List<ResLeaveReqDTO> resLeaveReqDTOList = this.leaveReqRepository.findByUserOrderBySendDateDesc(user).stream().map(leaveReqMapper::convertToResLeaveReqDTO).collect(Collectors.toList());
        return resLeaveReqDTOList;
    }
}
