package vn.bookstore.app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.response.ResRoleDTO;
import vn.bookstore.app.dto.response.ResResponse;
import vn.bookstore.app.model.Role;
import vn.bookstore.app.model.User;
import vn.bookstore.app.service.RoleService;
import vn.bookstore.app.service.impl.RoleServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Roles")
public class RoleController {
    private final RoleServiceImpl roleService;
    
    @GetMapping("/roles")
    public ResponseEntity<ResResponse<List<ResRoleDTO>>> getAllRole() {
        List<ResRoleDTO> roleList = this.roleService.getAllRole();
        return ResponseEntity.ok(
                new ResResponse<>(
                        200,
                        null,
                        "Get all roles successfully",
                        roleList
                )
        );
    }
    
    @GetMapping("/roles/{id}")
    public ResponseEntity<ResResponse<ResRoleDTO>> getRoleById(@PathVariable Long id) {
        ResRoleDTO role = this.roleService.handleRoleById(id);
        return ResponseEntity.ok(
                new ResResponse<>(
                        200,
                        null,
                        "Get role successfully",
                        role
                )
        );
    }
    
    @PostMapping("/roles")
    public ResponseEntity<ResResponse<ResRoleDTO>> createRole(@Valid @RequestBody Role role) {
        ResRoleDTO newRole = this.roleService.handleCreateRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResResponse<>(
                        200,
                        null,
                        "Create role successfully",
                        newRole
                )
        );
    }
    
    @PutMapping("/roles/{id}")
    public ResponseEntity<ResResponse<ResRoleDTO>> updateRole(@Valid @RequestBody Role role, @PathVariable Long id) {
        ResRoleDTO updatedRole = this.roleService.handleUpdateRole(role, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResResponse<>(
                        200,
                        null,
                        "Update role successfully",
                        updatedRole
                )
        );
    }
    
    @PatchMapping("/roles/{id}")
    public ResponseEntity<ResResponse> deleteRole(@PathVariable Long id) {
        this.roleService.handleDeleteRole(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResResponse<>(
                        200,
                        null,
                        "Delete role successfully",
                        null
                )
        );
    }
}
