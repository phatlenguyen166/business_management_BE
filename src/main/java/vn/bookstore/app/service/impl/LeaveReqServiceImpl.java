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
import vn.bookstore.app.util.error.NotFoundException;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveReqServiceImpl implements LeaveReqService {
    private final LeaveReqMapper leaveReqMapper;
    private final LeaveReqRepository leaveReqRepository;
    private final UserRepository userRepository;

    @Override
    public ResLeaveReqDTO handleCreateLeaveReq(ReqLeaveReqDTO leaveReqDTO) {
        LeaveRequest newLeaveReq = this.leaveReqMapper.convertToLeaveRequest(leaveReqDTO);
        User user = this.userRepository.findUserByIdAndStatus(leaveReqDTO.getUserId(),1).orElseThrow(() -> new NotFoundException("User Không tồn tại"));
        newLeaveReq.setStatus(2);
        newLeaveReq.setUser(user);
        this.leaveReqRepository.save(newLeaveReq);
        return this.leaveReqMapper.convertToResLeaveReqDTO(newLeaveReq);
    }

    @Override
    public List<ResLeaveReqDTO> handleGetAllLeaveReq() {
        List<ResLeaveReqDTO> resLeaveReqDTOList = this.leaveReqRepository.findByStatusIn(List.of(1,2))
                .stream()
                .map(leaveReqMapper ::convertToResLeaveReqDTO)
                .collect(Collectors.toList());
        return resLeaveReqDTOList;
    }



    @Override
    public ResLeaveReqDTO handleGetLeaveReq(Long id) {
        return this.leaveReqMapper.convertToResLeaveReqDTO(getLeaveReqById(id));
    }

    @Override
    public LeaveRequest getLeaveReqById(Long id) {
        return this.leaveReqRepository.findByIdAndStatusIn(id, List.of(1,2)).orElseThrow(() -> new NotFoundException("Đơn xin nghỉ phép không tồn tại"));
    }

    @Override
    public ResLeaveReqDTO handleUpdateLeaveReq(Long id, ReqLeaveReqDTO leaveRequest) {
        LeaveRequest currentLeaveReq = getLeaveReqById(id);
        this.leaveReqMapper.updateLeaveReq(leaveRequest, currentLeaveReq);
        this.leaveReqRepository.save(currentLeaveReq);
        return this.leaveReqMapper.convertToResLeaveReqDTO(currentLeaveReq);
    }

    @Override
    public void handleApproveLeaveReq(Long id) {
        LeaveRequest currentLeaveReq = getLeaveReqById(id);
        currentLeaveReq.setStatus(2);
        this.leaveReqRepository.save(currentLeaveReq);
    }

    @Override
    public void handleDeleteLeaveReq(Long id) {
        LeaveRequest currentLeaveReq = getLeaveReqById(id);
        currentLeaveReq.setStatus(0);
        this.leaveReqRepository.save(currentLeaveReq);
    }

    @Override
    public List<ResLeaveReqDTO> handleGetAllLeaveReqByUserId(Long userId) {
        User user = this.userRepository.findUserByIdAndStatus(userId,1).orElseThrow(() -> new NotFoundException("User Không tồn tại"));
        List<ResLeaveReqDTO> resLeaveReqDTOList = this.leaveReqRepository.findByUserAndStatusIn(user,List.of(1,2))
                .stream()
                .map(leaveReqMapper ::convertToResLeaveReqDTO)
                .collect(Collectors.toList());
        return resLeaveReqDTOList;
    }
}
