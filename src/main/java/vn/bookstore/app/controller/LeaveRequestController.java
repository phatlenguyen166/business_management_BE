package vn.bookstore.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.bookstore.app.dto.request.ReqLeaveReqDTO;
import vn.bookstore.app.dto.response.ResLeaveReqDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.impl.LeaveReqServiceImpl;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LeaveRequestController {

    private final LeaveReqServiceImpl leaveReqService;

    @PostMapping("/leaveReqs")
    public ResponseEntity<ResponseDTO<List<ResLeaveReqDTO>>> createLeaveReq(@Valid @RequestBody ReqLeaveReqDTO leaveRequest) {
        List<ResLeaveReqDTO> newLeaveReq = this.leaveReqService.handleCreateLeaveReq(leaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO<>(
                        201,
                        true,
                        null,
                        "Create LeaveRequest successfully",
                        newLeaveReq
                )
        );
    }

    @GetMapping("/leaveReqs")
    public ResponseEntity<ResponseDTO<List<ResLeaveReqDTO>>> getAllLeaveReq() {
        List<ResLeaveReqDTO> leaveRequests = this.leaveReqService.handleGetAllLeaveReq();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all LeaveRequest successfully",
                        leaveRequests
                )
        );
    }

    @GetMapping("/leaveReqs/user/{id}")
    public ResponseEntity<ResponseDTO<List<ResLeaveReqDTO>>> getAllLeaveReqByUserId(@PathVariable Long id) {
        List<ResLeaveReqDTO> leaveRequests = this.leaveReqService.handleGetAllLeaveReqByUserId(id);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all LeaveRequest By User successfully",
                        leaveRequests
                )
        );
    }

    @GetMapping("/leaveReqs/{id}")
    public ResponseEntity<ResponseDTO<ResLeaveReqDTO>> getLeaveReq(@PathVariable Long id) {
        ResLeaveReqDTO leaveRequest = this.leaveReqService.handleGetLeaveReq(id);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get LeaveRequest successfully",
                        leaveRequest
                )
        );
    }

    @PutMapping("/leaveReqs/{id}")
    public ResponseEntity<ResponseDTO<List<ResLeaveReqDTO>>> updateLeaveReq(@Valid @RequestBody ReqLeaveReqDTO leaveRequest, @PathVariable Long id) {
        List<ResLeaveReqDTO> updatedLeaveReq = this.leaveReqService.handleUpdateLeaveReq(id, leaveRequest);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Update LeaveRequest successfully",
                        updatedLeaveReq
                )
        );
    }

    @PatchMapping("/leaveReqs/approve/{id}")
    public ResponseEntity<ResponseDTO<Void>> approveLeaveReq(@PathVariable Long id) {
        this.leaveReqService.handleApproveLeaveReq(id);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Approve LeaveRequest successfully",
                        null
                )
        );
    }

    @PatchMapping("/leaveReqs/reject/{id}")
    public ResponseEntity<ResponseDTO<Void>> rejectLeaveReq(@PathVariable Long id) {
        this.leaveReqService.handleRejectLeaveReq(id);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Reject LeaveRequest successfully",
                        null
                )
        );
    }

    @PatchMapping("/leaveReqs/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteLeaveReq(@PathVariable Long id) {
        this.leaveReqService.handleDeleteLeaveReq(id);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Delete LeaveRequest successfully",
                        null
                )
        );
    }

}
