package vn.bookstore.app.service;

import vn.bookstore.app.dto.request.ReqSeniorityLevelDTO;
import vn.bookstore.app.dto.response.ResSeniorityDTO;
import vn.bookstore.app.model.SeniorityLevel;

import java.util.List;
import java.util.Optional;

public interface SeniorityLevelService {
    public ResSeniorityDTO handleCreateSeniorityLevel(ReqSeniorityLevelDTO reqSeniorityLevel);
    public List<ResSeniorityDTO> handleGetAllSeniority();
    public ResSeniorityDTO handleGetSeniorityById(Long id);
    public ResSeniorityDTO handleUpdateSeniority(ReqSeniorityLevelDTO reqSeniorityLevel, Long id);
    public void handleDeleteSeniority(Long id);
    public void handleAcceptSeniority(Long id);
    public List<ResSeniorityDTO> handleGetAllSeniorityByRoleId(Long roleId);
}
