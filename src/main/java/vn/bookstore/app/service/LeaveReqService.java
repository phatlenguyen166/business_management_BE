package vn.bookstore.app.service;

import vn.bookstore.app.dto.request.ReqLeaveReqDTO;
import vn.bookstore.app.dto.response.ResLeaveReqDTO;
import vn.bookstore.app.model.LeaveRequest;

import java.util.List;

public interface LeaveReqService {
    public ResLeaveReqDTO handleCreateLeaveReq(ReqLeaveReqDTO newLeaveReq);
    public List<ResLeaveReqDTO> handleGetAllLeaveReq();
    public ResLeaveReqDTO handleGetLeaveReq(Long id);
    public LeaveRequest getLeaveReqById(Long id);
    public ResLeaveReqDTO handleUpdateLeaveReq(Long id, ReqLeaveReqDTO leaveRequest);
    public void  handleApproveLeaveReq(Long id);
    public void  handleDeleteLeaveReq(Long id);
    public List<ResLeaveReqDTO> handleGetAllLeaveReqByUserId(Long userId);
}
