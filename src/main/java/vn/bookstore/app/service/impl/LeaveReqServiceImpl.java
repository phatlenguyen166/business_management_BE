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
import vn.bookstore.app.util.error.NotFoundValidException;

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


    private void validatePaidLeave(ReqLeaveReqDTO leaveReqDTO, User user) {
        long daysRequested = calculateLeaveDays(leaveReqDTO.getStartDate(), leaveReqDTO.getEndDate());
        if (daysRequested >12) {
            throw new InvalidRequestException("Nghỉ phép chỉ được tối đa " + 12 + " ngày 1 năm");
        }
        long totalPaidLeaves = this.leaveReqRepository.findLeaveRequestsByUserAndYear(user,leaveReqDTO.getStartDate().getYear(),LeaveTypeEnum.PAID_LEAVE,1)
                 .stream()
                 .mapToLong(LeaveRequest::getTotalDayLeave)
                 .sum();

         if (totalPaidLeaves + daysRequested > 12) {
             throw new InvalidRequestException("Bạn đã nghỉ phép " + totalPaidLeaves + " lần trong năm " + leaveReqDTO.getStartDate().getYear() + ". Số ngày nghỉ tối đa là 12 ngày. Không thể nghỉ thêm"  );
         }
    }

    private void validateYearlyLeave(ReqLeaveReqDTO leaveReqDTO, User user, LeaveTypeEnum leaveType, int maxDays) {
        long daysRequested = calculateLeaveDays(leaveReqDTO.getStartDate(), leaveReqDTO.getEndDate());
        if (daysRequested > maxDays) {
            String leaveName = leaveType == LeaveTypeEnum.SICK_LEAVE ? "ốm" : "thai sản";
            throw new InvalidRequestException("Nghỉ phép " + leaveName + " chỉ được tối đa " + maxDays + " ngày 1 năm");
        }
        // Lấy các đơn nghỉ đã tồn tại trong năm của ngày bắt đầu đơn
        List<LeaveRequest> existingRequests = this.leaveReqRepository.findByUserAndStatusAndYear(user, leaveReqDTO.getStartDate(), leaveType);
        long totalExistingDays = existingRequests.stream()
                .mapToLong(lr -> calculateLeaveDays(lr.getStartDate(), lr.getEndDate()))
                .sum();
        if ((totalExistingDays + daysRequested) > maxDays) {
            String leaveName = leaveType == LeaveTypeEnum.SICK_LEAVE ? "ốm" : "thai sản";
            throw new InvalidRequestException("Nghỉ phép " + leaveName + " chỉ được tối đa " + maxDays + " ngày 1 năm");
        }
    }

//    @Override
//    public List<ResLeaveReqDTO> handleCreateLeaveReq(ReqLeaveReqDTO leaveReqDTO) {
//        List<ResLeaveReqDTO> resLeaveReqDTOS = new ArrayList<>();
//        User user = this.userRepository.findUserByIdAndStatus(leaveReqDTO.getUserId(), 1).orElseThrow(() -> new NotFoundException("User Không tồn tại"));
//        if (leaveReqDTO.getStartDate().getYear() != leaveReqDTO.getEndDate().getYear()) {
//            LocalDate startDate1 = leaveReqDTO.getStartDate();
//            LocalDate endDate1 = LocalDate.of(leaveReqDTO.getStartDate().getYear(), 12, 31);
//            LocalDate startDate2 = LocalDate.of(leaveReqDTO.getEndDate().getYear(), 1, 1);
//            LocalDate endDate2 = leaveReqDTO.getEndDate();
//            LeaveRequest newLeaveReq1 = this.leaveReqMapper.convertToLeaveRequest(leaveReqDTO);
//            newLeaveReq1.setStartDate(startDate1);
//            newLeaveReq1.setEndDate(endDate1);
//            LeaveRequest newLeaveReq2 = this.leaveReqMapper.convertToLeaveRequest(leaveReqDTO);
//            newLeaveReq2.setStartDate(startDate2);
//            newLeaveReq2.setEndDate(endDate2);
//            if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.SICK_LEAVE) {
//                List<LeaveRequest> leaveRequestExists = this.leaveReqRepository.findByUserAndStatusAndYear(user, leaveReqDTO.getStartDate(), LeaveTypeEnum.SICK_LEAVE);
//                if (calculateLeaveDays(startDate1, endDate1) > 30) {
//                    throw new InvalidRequestException("Nghỉ phép ốm chỉ được tối đa 30 ngày 1 năm");
//                }
//                if (!leaveRequestExists.isEmpty()) {
//                    long totalDay = leaveRequestExists.stream().mapToLong(lr -> calculateLeaveDays(lr.getStartDate(), lr.getEndDate())).sum();
//                    if ((totalDay + calculateLeaveDays(startDate1, endDate1)) > 30) {
//                        throw new InvalidRequestException("Nghỉ phép ốm chỉ được tối đa 30 ngày 1 năm");
//                    } else if ((totalDay + calculateLeaveDays(startDate1, endDate1)) > 30) {
//                        throw new InvalidRequestException("Nghỉ phép ốm chỉ được tối đa 30 ngày 1 năm");
//                    } else {
//                        newLeaveReq1.setTotalDayLeave(calculateLeaveDays(startDate1, endDate1));
//                        newLeaveReq1.setStatus(2);
//                        newLeaveReq1.setUser(user);
//                        this.leaveReqRepository.save(newLeaveReq1);
//                        resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq1));
//                        newLeaveReq2.setTotalDayLeave(calculateLeaveDays(startDate2, endDate2));
//                        newLeaveReq2.setStatus(2);
//                        newLeaveReq2.setUser(user);
//                        this.leaveReqRepository.save(newLeaveReq2);
//                        resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq2));
//                        return resLeaveReqDTOS;
//                    }
//                }
//            } else if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.MATERNITY_LEAVE) {
//                List<LeaveRequest> leaveRequestExists = this.leaveReqRepository.findByUserAndStatusAndYear(user, leaveReqDTO.getStartDate(), LeaveTypeEnum.MATERNITY_LEAVE);
//                if (calculateLeaveDays(startDate1, endDate1) > 180) {
//                    throw new InvalidRequestException("Nghỉ phép thai sản chỉ được tối đa 180 ngày 1 năm");
//                }
//                if (!leaveRequestExists.isEmpty()) {
//                    long totalDay = leaveRequestExists.stream().mapToLong(lr -> calculateLeaveDays(lr.getStartDate(), lr.getEndDate())).sum();
//                    if ((totalDay + calculateLeaveDays(startDate1, endDate1)) > 180) {
//                        throw new InvalidRequestException("Nghỉ phép thai sản chỉ được tối đa 180 ngày 1 năm");
//                    } else if ((totalDay + calculateLeaveDays(startDate1, endDate1)) > 180) {
//                        throw new InvalidRequestException("Nghỉ phép thai sản chỉ được tối đa 180 ngày 1 năm");
//                    } else {
//                        newLeaveReq1.setTotalDayLeave(calculateLeaveDays(startDate1, endDate1));
//                        newLeaveReq1.setStatus(2);
//                        newLeaveReq1.setUser(user);
//                        this.leaveReqRepository.save(newLeaveReq1);
//                        resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq1));
//                        newLeaveReq2.setTotalDayLeave(calculateLeaveDays(startDate2, endDate2));
//                        newLeaveReq2.setStatus(2);
//                        newLeaveReq2.setUser(user);
//                        this.leaveReqRepository.save(newLeaveReq2);
//                        resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq2));
//                        return resLeaveReqDTOS;
//                    }
//                }
//            }
//        }
//            LeaveRequest newLeaveReq = this.leaveReqMapper.convertToLeaveRequest(leaveReqDTO);
//            if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.PAID_LEAVE) {
//                if (leaveReqDTO.getEndDate().isAfter(leaveReqDTO.getStartDate())) {
//                    throw new InvalidRequestException("Nghỉ phép có lương chỉ được tối đa 1 ngày");
//                } else if (this.leaveReqRepository.findByUserAndStatusAndMonth(user, leaveReqDTO.getStartDate(), LeaveTypeEnum.PAID_LEAVE) != null) {
//                    throw new InvalidRequestException("Đã hết ngày nghỉ phép tháng này");
//                }
//            } else if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.SICK_LEAVE) {
//                List<LeaveRequest> leaveRequestExists = this.leaveReqRepository.findByUserAndStatusAndYear(user, leaveReqDTO.getStartDate(), LeaveTypeEnum.SICK_LEAVE);
//                if (calculateLeaveDays(leaveReqDTO.getStartDate(),leaveReqDTO.getEndDate()) > 30) {
//                    throw new InvalidRequestException("Nghỉ phép ốm chỉ được tối đa 30 ngày 1 năm");
//                } else if (!leaveRequestExists.isEmpty()) {
//                    long totalDay = leaveRequestExists.stream().mapToLong(lr -> calculateLeaveDays(lr.getStartDate(), lr.getEndDate())).sum();
//                    if ((totalDay + calculateLeaveDays(leaveReqDTO.getStartDate(),leaveReqDTO.getEndDate())) > 30) {
//                        throw new InvalidRequestException("Nghỉ phép ốm chỉ được tối đa 30 ngày 1 năm");
//                    }
//                }
//            } else if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.MATERNITY_LEAVE) {
//                List<LeaveRequest> leaveRequestExists = this.leaveReqRepository.findByUserAndStatusAndYear(user, leaveReqDTO.getStartDate(), LeaveTypeEnum.MATERNITY_LEAVE);
//                if (calculateLeaveDays(leaveReqDTO.getStartDate(),leaveReqDTO.getEndDate()) > 180) {
//                    throw new InvalidRequestException("Nghỉ phép thai sản chỉ được tối đa 180 ngày 1 năm");
//                } else if (!leaveRequestExists.isEmpty()) {
//                    long totalDay = leaveRequestExists.stream().mapToLong(lr -> calculateLeaveDays(lr.getStartDate(), lr.getEndDate())).sum();
//                    if ((totalDay + calculateLeaveDays(leaveReqDTO.getStartDate(),leaveReqDTO.getEndDate())) > 180) {
//                        throw new InvalidRequestException("Nghỉ phép thai sản chỉ được tối đa 180 ngày 1 năm");
//                    }
//                }
//            }
//         newLeaveReq.setTotalDayLeave(calculateLeaveDays(leaveReqDTO.getStartDate(), leaveReqDTO.getEndDate()));
//            newLeaveReq.setStatus(2);
//            newLeaveReq.setUser(user);
//            this.leaveReqRepository.save(newLeaveReq);
//            resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq));
//            return resLeaveReqDTOS;
//        }

    @Override
    public List<ResLeaveReqDTO> handleCreateLeaveReq(ReqLeaveReqDTO leaveReqDTO) {
        List<ResLeaveReqDTO> resLeaveReqDTOS = new ArrayList<>();
        User user = this.userRepository.findUserByIdAndStatus(leaveReqDTO.getUserId(), 1)
                .orElseThrow(() -> new NotFoundValidException("User Không tồn tại"));
        if (leaveReqDTO.getStartDate().getYear() != leaveReqDTO.getEndDate().getYear()) {
            LocalDate startDate1 = leaveReqDTO.getStartDate();
            LocalDate endDate1 = LocalDate.of(leaveReqDTO.getStartDate().getYear(), 12, 31);
            LocalDate startDate2 = LocalDate.of(leaveReqDTO.getEndDate().getYear(), 1, 1);
            LocalDate endDate2 = leaveReqDTO.getEndDate();
            if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.SICK_LEAVE) {
                ReqLeaveReqDTO part1 = leaveReqDTO.copy();
                part1.setStartDate(startDate1);
                part1.setEndDate(endDate1);
                validateYearlyLeave(part1, user, LeaveTypeEnum.SICK_LEAVE, 30);
                ReqLeaveReqDTO part2 = leaveReqDTO.copy();
                part2.setStartDate(startDate2);
                part2.setEndDate(endDate2);
                validateYearlyLeave(part2, user, LeaveTypeEnum.SICK_LEAVE, 30);
                LeaveRequest newLeaveReq1 = this.leaveReqMapper.convertToLeaveRequest(part1);
                newLeaveReq1.setTotalDayLeave(calculateLeaveDays(startDate1, endDate1));
                newLeaveReq1.setStatus(2);
                newLeaveReq1.setUser(user);
                this.leaveReqRepository.save(newLeaveReq1);
                resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq1));
                LeaveRequest newLeaveReq2 = this.leaveReqMapper.convertToLeaveRequest(part2);
                newLeaveReq2.setTotalDayLeave(calculateLeaveDays(startDate2, endDate2));
                newLeaveReq2.setStatus(2);
                newLeaveReq2.setUser(user);
                this.leaveReqRepository.save(newLeaveReq2);
                resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq2));
                return resLeaveReqDTOS;
            } else if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.MATERNITY_LEAVE) {
                ReqLeaveReqDTO part1 = leaveReqDTO.copy();
                part1.setStartDate(startDate1);
                part1.setEndDate(endDate1);
                validateYearlyLeave(part1, user, LeaveTypeEnum.MATERNITY_LEAVE, 180);
                ReqLeaveReqDTO part2 = leaveReqDTO.copy();
                part2.setStartDate(startDate2);
                part2.setEndDate(endDate2);
                validateYearlyLeave(part2, user, LeaveTypeEnum.MATERNITY_LEAVE, 180);
                LeaveRequest newLeaveReq1 = this.leaveReqMapper.convertToLeaveRequest(part1);
                newLeaveReq1.setTotalDayLeave(calculateLeaveDays(startDate1, endDate1));
                newLeaveReq1.setStatus(2);
                newLeaveReq1.setUser(user);
                this.leaveReqRepository.save(newLeaveReq1);
                resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq1));
                LeaveRequest newLeaveReq2 = this.leaveReqMapper.convertToLeaveRequest(part2);
                newLeaveReq2.setTotalDayLeave(calculateLeaveDays(startDate2, endDate2));
                newLeaveReq2.setStatus(2);
                newLeaveReq2.setUser(user);
                this.leaveReqRepository.save(newLeaveReq2);
                resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq2));
                return resLeaveReqDTOS;
            } else if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.PAID_LEAVE) {
                ReqLeaveReqDTO part1 = leaveReqDTO.copy();
                part1.setStartDate(startDate1);
                part1.setEndDate(endDate1);
                validatePaidLeave(part1,user);
                ReqLeaveReqDTO part2 = leaveReqDTO.copy();
                part2.setStartDate(startDate2);
                part2.setEndDate(endDate2);
                validatePaidLeave(part2,user);
                LeaveRequest newLeaveReq1 = this.leaveReqMapper.convertToLeaveRequest(part1);
                newLeaveReq1.setTotalDayLeave(calculateLeaveDays(startDate1, endDate1));
                newLeaveReq1.setStatus(2);
                newLeaveReq1.setUser(user);
                this.leaveReqRepository.save(newLeaveReq1);
                resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq1));
                LeaveRequest newLeaveReq2 = this.leaveReqMapper.convertToLeaveRequest(part2);
                newLeaveReq2.setTotalDayLeave(calculateLeaveDays(startDate2, endDate2));
                newLeaveReq2.setStatus(2);
                newLeaveReq2.setUser(user);
                this.leaveReqRepository.save(newLeaveReq2);
                resLeaveReqDTOS.add(this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq2));
                return resLeaveReqDTOS;
            }
        }
        if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.PAID_LEAVE) {
            validatePaidLeave(leaveReqDTO, user);
        } else if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.SICK_LEAVE) {
            validateYearlyLeave(leaveReqDTO, user, LeaveTypeEnum.SICK_LEAVE, 30);
        } else if (leaveReqDTO.getLeaveReason() == LeaveTypeEnum.MATERNITY_LEAVE) {
            validateYearlyLeave(leaveReqDTO, user, LeaveTypeEnum.MATERNITY_LEAVE, 180);
        }

        LeaveRequest newLeaveReq = this.leaveReqMapper.convertToLeaveRequest(leaveReqDTO);
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
        return this.leaveReqRepository.findByIdAndStatusIn(id, List.of(1, 2, 3)).orElseThrow(() -> new NotFoundValidException("Đơn xin nghỉ phép không tồn tại"));
    }

    @Override
    public List<ResLeaveReqDTO> handleUpdateLeaveReq(Long id, ReqLeaveReqDTO leaveRequestDTO) {
        LeaveRequest currentLeaveReq = this.leaveReqRepository.findByIdAndStatus(id,2).orElseThrow(() -> new InvalidRequestException("Đơn nghỉ phép không tồn tại hoặc đã được xử lý"));
        User user = currentLeaveReq.getUser();
        List<ResLeaveReqDTO> resLeaveReqDTOS = new ArrayList<>();

        if (leaveRequestDTO.getStartDate().getYear() != leaveRequestDTO.getEndDate().getYear()) {
            LocalDate startDate1 = leaveRequestDTO.getStartDate();
            LocalDate endDate1 = LocalDate.of(leaveRequestDTO.getStartDate().getYear(), 12, 31);
            LocalDate startDate2 = LocalDate.of(leaveRequestDTO.getEndDate().getYear(), 1, 1);
            LocalDate endDate2 = leaveRequestDTO.getEndDate();

            ReqLeaveReqDTO part1 = leaveRequestDTO.copy();
            part1.setStartDate(startDate1);
            part1.setEndDate(endDate1);
            ReqLeaveReqDTO part2 = leaveRequestDTO.copy();
            part2.setStartDate(startDate2);
            part2.setEndDate(endDate2);

            if (leaveRequestDTO.getLeaveReason() == LeaveTypeEnum.PAID_LEAVE) {
                validatePaidLeave(part1, user);
                validatePaidLeave(part2, user);
            } else if (leaveRequestDTO.getLeaveReason() == LeaveTypeEnum.SICK_LEAVE) {
                validateYearlyLeave(part1, user, LeaveTypeEnum.SICK_LEAVE, 30);
                validateYearlyLeave(part2, user, LeaveTypeEnum.SICK_LEAVE, 30);
            } else if (leaveRequestDTO.getLeaveReason() == LeaveTypeEnum.MATERNITY_LEAVE) {
                validateYearlyLeave(part1, user, LeaveTypeEnum.MATERNITY_LEAVE, 180);
                validateYearlyLeave(part2, user, LeaveTypeEnum.MATERNITY_LEAVE, 180);
            }

            leaveReqMapper.updateLeaveReq(part1, currentLeaveReq);
            currentLeaveReq.setTotalDayLeave(calculateLeaveDays(startDate1, endDate1));
            currentLeaveReq.setStatus(2);
            this.leaveReqRepository.save(currentLeaveReq);
            resLeaveReqDTOS.add(leaveReqMapper.convertToResLeaveReqDTO(currentLeaveReq));

            LeaveRequest newLeaveReq = leaveReqMapper.convertToLeaveRequest(part2);
            newLeaveReq.setTotalDayLeave(calculateLeaveDays(startDate2, endDate2));
            newLeaveReq.setStatus(2);
            newLeaveReq.setUser(user);
            this.leaveReqRepository.save(newLeaveReq);
            resLeaveReqDTOS.add(leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq));

            return resLeaveReqDTOS;
        } else {
            if (leaveRequestDTO.getLeaveReason() == LeaveTypeEnum.PAID_LEAVE) {
                validatePaidLeave(leaveRequestDTO, user);
            } else if (leaveRequestDTO.getLeaveReason() == LeaveTypeEnum.SICK_LEAVE) {
                validateYearlyLeave(leaveRequestDTO, user, LeaveTypeEnum.SICK_LEAVE, 30);
            } else if (leaveRequestDTO.getLeaveReason() == LeaveTypeEnum.MATERNITY_LEAVE) {
                validateYearlyLeave(leaveRequestDTO, user, LeaveTypeEnum.MATERNITY_LEAVE, 180);
            }
            leaveReqMapper.updateLeaveReq(leaveRequestDTO, currentLeaveReq);
            currentLeaveReq.setTotalDayLeave(calculateLeaveDays(leaveRequestDTO.getStartDate(), leaveRequestDTO.getEndDate()));
            this.leaveReqRepository.save(currentLeaveReq);
            return List.of(leaveReqMapper.convertToResLeaveReqDTO(currentLeaveReq));
        }
    }


    @Override
    public void handleApproveLeaveReq(Long id) {
        LeaveRequest currentLeaveReq = this.leaveReqRepository.findByIdAndStatus(id, 2).orElseThrow(() -> new InvalidRequestException("Đơn nghỉ phép không tồn tại hoặc đã được xử lý"));
        List<LeaveRequest> overlappingLeaveRequests = this.leaveReqRepository.findAllLeaveRequestByUserAndDateAndStatus(
                currentLeaveReq.getUser(), currentLeaveReq.getStartDate(), 1);
        overlappingLeaveRequests.addAll(this.leaveReqRepository.findAllLeaveRequestByUserAndDateAndStatus(
                currentLeaveReq.getUser(), currentLeaveReq.getEndDate(), 1));
        boolean hasOverlap = !overlappingLeaveRequests.isEmpty();
        if(hasOverlap) {
            throw new InvalidRequestException("Thời gian nghỉ không hợp lệ, đã tồn tại đơn nghỉ phép trong thời gian này: " +
                    overlappingLeaveRequests.stream()
                            .map(lr -> String.valueOf(lr.getId()))
                            .collect(Collectors.joining(", "))

            );
        }
        currentLeaveReq.setStatus(1);
        this.leaveReqRepository.save(currentLeaveReq);
    }

    @Override
    public void handleDeleteLeaveReq(Long id) {
        LeaveRequest currentLeaveReq = this.leaveReqRepository.findByIdAndStatus(id, 2).orElseThrow(() -> new NotFoundValidException("Đơn xin nghỉ phép không tồn tại hoặc đã được xử lý"));
        currentLeaveReq.setStatus(0);
        this.leaveReqRepository.save(currentLeaveReq);
    }

    @Override
    public void handleRejectLeaveReq(Long id) {
        LeaveRequest currentLeaveReq = this.leaveReqRepository.findByIdAndStatus(id, 2).orElseThrow(() -> new NotFoundValidException("Đơn xin nghỉ phép không tồn tại hoặc đã được xử lý"));
        currentLeaveReq.setStatus(3);
        this.leaveReqRepository.save(currentLeaveReq);
    }

    @Override
    public List<ResLeaveReqDTO> handleGetAllLeaveReqByUserId(Long userId) {
        User user = this.userRepository.findUserByIdAndStatus(userId, 1).orElseThrow(() -> new NotFoundValidException("User Không tồn tại"));
        List<ResLeaveReqDTO> resLeaveReqDTOList = this.leaveReqRepository.findByUserOrderBySendDateDesc(user).stream().map(leaveReqMapper::convertToResLeaveReqDTO).collect(Collectors.toList());
        return resLeaveReqDTOList;
    }
}
