package vn.bookstore.app.service.impl;

import org.springframework.stereotype.Service;
import vn.bookstore.app.model.SeniorityLevel;
import vn.bookstore.app.repository.SeniorityLevelRepository;
import vn.bookstore.app.service.SeniorityLevelService;

import java.util.List;

@Service
public class SeniorityLevelServiceImpl implements SeniorityLevelService {

    private SeniorityLevelRepository seniorityLevelRepository;
    public SeniorityLevelServiceImpl(SeniorityLevelRepository seniorityLevelRepository) {
        this.seniorityLevelRepository = seniorityLevelRepository;
    }

    @Override
    public SeniorityLevel handleCreateSeniorityLevel(SeniorityLevel reqSeniorityLevel) {
        reqSeniorityLevel.setStatus(1);
        return this.seniorityLevelRepository.save(reqSeniorityLevel);
    }

    @Override
    public List<SeniorityLevel> handleGetAllSeniority() {
        return this.seniorityLevelRepository.findAllByStatus(1);
    }

    @Override
    public SeniorityLevel handleGetSeniorityById(Long id) {
        return seniorityLevelRepository.findByIdAndStatus(id,1).orElse(null);
    }

    @Override
    public SeniorityLevel handleUpdateSeniority(SeniorityLevel reqSeniorityLevel, Long id) {
        SeniorityLevel currentSeniorityLevel = handleGetSeniorityById(id);
        currentSeniorityLevel.setLevel(reqSeniorityLevel.getLevel());
        currentSeniorityLevel.setDescription(reqSeniorityLevel.getDescription());
        currentSeniorityLevel.setLevelName(reqSeniorityLevel.getLevelName());
        currentSeniorityLevel.setSalaryCoefficient(reqSeniorityLevel.getSalaryCoefficient());
        return seniorityLevelRepository.save(currentSeniorityLevel);
    }

    @Override
    public void handleDeleteSeniority(Long id) {
        SeniorityLevel currentSeniorityLevel = handleGetSeniorityById(id);
        currentSeniorityLevel.setStatus(0);
        seniorityLevelRepository.save(currentSeniorityLevel);
    }
}
