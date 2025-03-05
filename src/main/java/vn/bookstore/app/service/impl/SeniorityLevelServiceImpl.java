package vn.bookstore.app.service.impl;

import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqSeniorityLevelDTO;
import vn.bookstore.app.mapper.SeniorityLevelMapper;
import vn.bookstore.app.model.Role;
import vn.bookstore.app.model.SeniorityLevel;
import vn.bookstore.app.repository.RoleRepository;
import vn.bookstore.app.repository.SeniorityLevelRepository;
import vn.bookstore.app.service.SeniorityLevelService;
import vn.bookstore.app.util.error.ExistingIdException;
import vn.bookstore.app.util.error.InvalidDataException;
import vn.bookstore.app.util.error.InvalidRequestException;

import java.util.List;
import java.util.Optional;

@Service
public class SeniorityLevelServiceImpl implements SeniorityLevelService {

    private SeniorityLevelRepository seniorityLevelRepository;
    private RoleRepository roleRepository;
    private SeniorityLevelMapper seniorityLevelMapper;
    public SeniorityLevelServiceImpl(SeniorityLevelRepository seniorityLevelRepository,
                                     RoleRepository roleRepository,
                                     SeniorityLevelMapper seniorityLevelMapper) {
        this.seniorityLevelRepository = seniorityLevelRepository;
        this.roleRepository = roleRepository;
        this.seniorityLevelMapper = seniorityLevelMapper;
    }

    @Override
    public SeniorityLevel handleCreateSeniorityLevel(ReqSeniorityLevelDTO reqSeniorityLevel) throws InvalidRequestException {
        if (this.seniorityLevelRepository.existsByLevelNameAndStatus(reqSeniorityLevel.getLevelName(),1)) {
            throw new ExistingIdException("Tên cấp bậc đã tồn tại");
        } else {
            Role role = this.roleRepository.findByIdAndStatus(reqSeniorityLevel.getRoleId(), 1).orElseThrow(() -> new InvalidRequestException("Role không tồn tại"));
            SeniorityLevel newSeniorityLevel = this.seniorityLevelMapper.convertoSeniorityLevel(reqSeniorityLevel);
            newSeniorityLevel.setRole(role);
            newSeniorityLevel.setStatus(1);
            return this.seniorityLevelRepository.save(newSeniorityLevel);
        }

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
    public SeniorityLevel handleUpdateSeniority(ReqSeniorityLevelDTO reqSeniorityLevel, Long id) {
        SeniorityLevel currentSeniorityLevel = handleGetSeniorityById(id);
        seniorityLevelMapper.updateSeniorityLevelFromDTO(reqSeniorityLevel, currentSeniorityLevel);
        return seniorityLevelRepository.save(currentSeniorityLevel);
    }

    @Override
    public void handleDeleteSeniority(Long id) {
        SeniorityLevel currentSeniorityLevel = handleGetSeniorityById(id);
        currentSeniorityLevel.setStatus(0);
        seniorityLevelRepository.save(currentSeniorityLevel);
    }

    @Override
    public List<SeniorityLevel> handleGetAllSeniorityByRoleId(Long roleId) {
        Role role = this.roleRepository.findByIdAndStatus(roleId,1).orElseThrow(()-> new InvalidRequestException("Role không tồn tại"));
        return this.seniorityLevelRepository.findAllByStatusAndRole(1,role);
    }
}
