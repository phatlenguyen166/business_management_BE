package vn.bookstore.app.service;

import vn.bookstore.app.model.SeniorityLevel;

import java.util.List;

public interface SeniorityLevelService {
    public SeniorityLevel handleCreateSeniorityLevel(SeniorityLevel reqSeniorityLevel);
    public List<SeniorityLevel> handleGetAllSeniority();
    public SeniorityLevel handleGetSeniorityById(Long id);
    public SeniorityLevel handleUpdateSeniority(SeniorityLevel reqSeniorityLevel, Long id);
    public void handleDeleteSeniority(Long id);
}
