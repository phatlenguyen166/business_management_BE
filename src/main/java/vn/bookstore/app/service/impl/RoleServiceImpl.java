package vn.bookstore.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.response.ResRoleDTO;
import vn.bookstore.app.mapper.RoleMapper;
import vn.bookstore.app.model.Role;
import vn.bookstore.app.repository.RoleRepository;
import vn.bookstore.app.service.RoleService;
import vn.bookstore.app.util.error.ExistingIdException;
import vn.bookstore.app.util.error.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<ResRoleDTO> getAllRole() {
        List<ResRoleDTO> roles = this.roleRepository.findAllByStatus(1)
                .stream()
                .map(roleMapper::convertToResRoleDTO)
                .collect(Collectors.toList());
        return roles;
    }

    @Override
    public ResRoleDTO handleRoleById(Long id) {
        return this.roleMapper.convertToResRoleDTO(this.roleRepository.findByIdAndStatus(id,1).orElseThrow(() -> new NotFoundException("Role không tồn tại")));
    }

    @Override
    public ResRoleDTO handleCreateRole(Role role) {
        if (this.roleRepository.existsByNameAndStatus(role.getName(),1)){
            throw new ExistingIdException("Role đã tồn tại trong hệ thống");
        }
        role.setStatus(1);
        Role newRole =  this.roleRepository.save(role);
        return this.roleMapper.convertToResRoleDTO(newRole);
    }

    @Override
    public ResRoleDTO handleUpdateRole(Role role, Long id) {
        Role currentRole = this.roleRepository.findByIdAndStatus(id,1).orElseThrow(() -> new NotFoundException("Role không tồn tại"));
        this.roleMapper.updateRole(role,currentRole);
        currentRole.setStatus(1);
        this.roleRepository.save(currentRole);
        return this.roleMapper.convertToResRoleDTO(currentRole);
    }

    @Override
    public void handleDeleteRole(Long id) {
        Role currentRole = this.roleRepository.findByIdAndStatus(id,1).orElseThrow(() -> new NotFoundException("Role không tồn tại"));
        currentRole.setStatus(0);
        this.roleRepository.save(currentRole);
    }
}
