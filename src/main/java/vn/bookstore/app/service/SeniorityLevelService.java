package vn.bookstore.app.service;

import vn.bookstore.app.dto.request.ReqSeniorityLevelDTO;
import vn.bookstore.app.model.SeniorityLevel;

import java.util.List;
import java.util.Optional;

public interface SeniorityLevelService {
    public SeniorityLevel handleCreateSeniorityLevel(ReqSeniorityLevelDTO reqSeniorityLevel);
    public List<SeniorityLevel> handleGetAllSeniority();
    public SeniorityLevel handleGetSeniorityById(Long id);
    public SeniorityLevel handleUpdateSeniority(ReqSeniorityLevelDTO reqSeniorityLevel, Long id);
    public void handleDeleteSeniority(Long id);
    public List<SeniorityLevel> handleGetAllSeniorityByRoleId(Long roleId);
}
