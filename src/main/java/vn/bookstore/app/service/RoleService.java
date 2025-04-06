package vn.bookstore.app.service;


import vn.bookstore.app.dto.request.ReqRoleDTO;
import vn.bookstore.app.dto.response.ResRoleDTO;
import vn.bookstore.app.model.Role;

import java.util.List;

public interface RoleService {
    public List<ResRoleDTO> getAllRole();
    public ResRoleDTO handleRoleById(Long id);
    public ResRoleDTO handleCreateRole(ReqRoleDTO role);
    public ResRoleDTO handleUpdateRole(ReqRoleDTO role, Long id);
    void handleDeleteRole(Long id);
    void handleAcceptRole(Long id);

    Role getRoleByUserId(Long id);


}
