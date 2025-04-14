package vn.bookstore.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.bookstore.app.dto.request.ReqSeniorityLevelDTO;
import vn.bookstore.app.dto.response.ResSeniorityDTO;
import vn.bookstore.app.mapper.SeniorityLevelMapper;
import vn.bookstore.app.model.Role;
import vn.bookstore.app.model.SeniorityLevel;
import vn.bookstore.app.repository.RoleRepository;
import vn.bookstore.app.repository.SeniorityLevelRepository;
import vn.bookstore.app.service.SeniorityLevelService;
import vn.bookstore.app.util.error.ExistingIdException;
import vn.bookstore.app.util.error.InvalidRequestException;
import vn.bookstore.app.util.error.NotFoundValidException;

@Service
public class SeniorityLevelServiceImpl implements SeniorityLevelService {

    private final SeniorityLevelRepository seniorityLevelRepository;
    private final RoleRepository roleRepository;
    private final SeniorityLevelMapper seniorityLevelMapper;

    public SeniorityLevelServiceImpl(SeniorityLevelRepository seniorityLevelRepository,
            RoleRepository roleRepository,
            SeniorityLevelMapper seniorityLevelMapper) {
        this.seniorityLevelRepository = seniorityLevelRepository;
        this.roleRepository = roleRepository;
        this.seniorityLevelMapper = seniorityLevelMapper;
    }

    @Override
    public ResSeniorityDTO handleCreateSeniorityLevel(ReqSeniorityLevelDTO reqSeniorityLevel) throws InvalidRequestException {
        if (this.seniorityLevelRepository.existsByLevelNameAndStatusIn(reqSeniorityLevel.getLevelName(), List.of(1, 2))) {
            throw new ExistingIdException("Tên cấp bậc đã tồn tại");
        }
        Role role = this.roleRepository.findByIdAndStatus(reqSeniorityLevel.getRoleId(), 1).orElseThrow(() -> new InvalidRequestException("Role không tồn tại"));
        SeniorityLevel newSeniorityLevel = this.seniorityLevelMapper.convertoSeniorityLevel(reqSeniorityLevel);
        newSeniorityLevel.setRole(role);
        newSeniorityLevel.setStatus(2);
        SeniorityLevel newSeniority = this.seniorityLevelRepository.save(newSeniorityLevel);
        return this.seniorityLevelMapper.convertToResSeniorityDTO(newSeniority);
    }

    @Override
    public List<ResSeniorityDTO> handleGetAllSeniority() {
        return this.seniorityLevelRepository.findAllByStatusIn(List.of(1, 2)).stream()
                .map(seniorityLevelMapper::convertToResSeniorityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResSeniorityDTO handleGetSeniorityById(Long id) {
        SeniorityLevel seniorityLevel = seniorityLevelRepository.findSeniorityLevelByIdAndStatusIn(id, List.of(1, 2)).
                orElseThrow(() -> new NotFoundValidException("Cap bac khong ton tai!"));

        return this.seniorityLevelMapper.convertToResSeniorityDTO(seniorityLevel);
    }

    @Override
    public ResSeniorityDTO handleUpdateSeniority(ReqSeniorityLevelDTO reqSeniorityLevel, Long id) {
        SeniorityLevel currentSeniorityLevel = seniorityLevelRepository.findSeniorityLevelByIdAndStatusIn(id, List.of(2)).
                orElseThrow(() -> new NotFoundValidException("Cấp bậc không tồn tại hoặc đã được cấp phép"));
        if (reqSeniorityLevel.getSalaryCoefficient() > 5) {
            throw new InvalidRequestException("Hệ số lương không được lớn hơn 5");
        }
        seniorityLevelMapper.updateSeniorityLevelFromDTO(reqSeniorityLevel, currentSeniorityLevel);
        if (reqSeniorityLevel.getStatus() == 1) {
            currentSeniorityLevel.setStatus(1);
            this.seniorityLevelRepository.save(currentSeniorityLevel);
        } else if (currentSeniorityLevel.getStatus() == 1 && reqSeniorityLevel.getStatus() == 2) {
            throw new InvalidRequestException("Không thể thay đổi trạng thái");
        }
        SeniorityLevel updatedSeniorityLevel = seniorityLevelRepository.save(currentSeniorityLevel);
        return this.seniorityLevelMapper.convertToResSeniorityDTO(updatedSeniorityLevel);
    }

    @Override
    public void handleDeleteSeniority(Long id) {
        SeniorityLevel currentSeniorityLevel = seniorityLevelRepository.findSeniorityLevelByIdAndStatusIn(id, List.of(2)).
                orElseThrow(() -> new NotFoundValidException("Cấp bậc không tồn tại hoặc đã được cấp phép"));
        currentSeniorityLevel.setStatus(0);
        seniorityLevelRepository.save(currentSeniorityLevel);
    }

    @Override
    public void handleAcceptSeniority(Long id) {
        SeniorityLevel currentSeniorityLevel = seniorityLevelRepository.findSeniorityLevelByIdAndStatusIn(id, List.of(2)).
                orElseThrow(() -> new NotFoundValidException("Cấp bậc không tồn tại hoặc đã được cấp phép"));
        currentSeniorityLevel.setStatus(1);
        seniorityLevelRepository.save(currentSeniorityLevel);
    }

    @Override
    public List<ResSeniorityDTO> handleGetAllSeniorityByRoleId(Long roleId) {
        Role role = this.roleRepository.findByIdAndStatusIn(roleId, List.of(1)).orElseThrow(() -> new NotFoundValidException("Role không tồn tại hoặc chưa được cấp phép"));
        return this.seniorityLevelRepository.findAllByStatusInAndRole(List.of(1, 2), role).stream()
                .map(seniorityLevelMapper::convertToResSeniorityDTO)
                .collect(Collectors.toList());
    }
}
