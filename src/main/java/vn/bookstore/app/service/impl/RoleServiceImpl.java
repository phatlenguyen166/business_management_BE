package vn.bookstore.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqRoleDTO;
import vn.bookstore.app.dto.response.ResRoleDTO;
import vn.bookstore.app.mapper.RoleMapper;
import vn.bookstore.app.model.Allowance;
import vn.bookstore.app.model.Role;
import vn.bookstore.app.repository.AllowanceRepository;
import vn.bookstore.app.repository.RoleRepository;
import vn.bookstore.app.service.RoleService;
import vn.bookstore.app.util.error.ExistingIdException;
import vn.bookstore.app.util.error.NotFoundValidException;
import vn.bookstore.app.util.error.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final AllowanceRepository allowanceRepository;

    @Override
    public List<ResRoleDTO> getAllRole() {
        return this.roleRepository.findAllByStatusIn(List.of(1,2))
                .stream()
                .map(roleMapper::convertToResRoleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResRoleDTO handleRoleById(Long id) {
        return this.roleMapper.convertToResRoleDTO(this.roleRepository.findByIdAndStatus(id, 1).orElseThrow(() -> new NotFoundValidException("Role không tồn tại")));
    }

    @Override
    public ResRoleDTO handleCreateRole(ReqRoleDTO role) {
        if (this.roleRepository.existsByNameAndStatusIn(role.getName(), List.of(1,2))) {
            throw new ExistingIdException("Role đã tồn tại trong hệ thống");
        }
        Allowance allowance = this.allowanceRepository.findAllowanceById(role.getAllowanceId()).
                orElseThrow(() -> new NotFoundValidException("Phụ cấp không tồn tại"));
        Role newRole = this.roleMapper.convertoRole(role);
        newRole.setAllowance(allowance);
        newRole.setStatus(2);
         this.roleRepository.save(newRole);
        return this.roleMapper.convertToResRoleDTO(newRole);
    }

    @Override
    public ResRoleDTO handleUpdateRole(ReqRoleDTO role, Long id) {
        Role currentRole = this.roleRepository.findByIdAndStatus(id, 2).orElseThrow(() -> new NotFoundValidException("Role không tồn tại hoặc đã được cấp phép"));
        Role updateRole = this.roleMapper.convertoRole(role);
        this.roleMapper.updateRole(updateRole, currentRole);
        if(currentRole.getStatus() == 2) {
            currentRole.setStatus(role.getStatus());
            this.roleRepository.save(currentRole);
        }
        this.roleRepository.save(currentRole);
        return this.roleMapper.convertToResRoleDTO(currentRole);
    }

    @Override
    public void handleDeleteRole(Long id) {
        Role currentRole = this.roleRepository.findByIdAndStatus(id, 2).orElseThrow(() -> new NotFoundValidException("Role không tồn tại hoặc đã được cấp phép"));
        currentRole.setStatus(0);
        this.roleRepository.save(currentRole);
    }

    @Override
    public void handleAcceptRole(Long id) {
        Role currentRole = this.roleRepository.findByIdAndStatus(id, 2).orElseThrow(() -> new NotFoundValidException("Role không tồn tại hoặc đã được cấp phép"));
        currentRole.setStatus(1);
        this.roleRepository.save(currentRole);
    }

    @Override
    public Role getRoleByUserId(Long id) {
        return roleRepository.findRoleByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nhân viên đã chấm dứt hợp đồng"));
    }
}
