package vn.bookstore.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.response.ResRoleDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.model.Role;
import vn.bookstore.app.service.impl.RoleServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoleController {
    private final RoleServiceImpl roleService;

    @GetMapping("/roles")
    public ResponseEntity<ResponseDTO<List<ResRoleDTO>>>  getAllRole() {
        List<ResRoleDTO> roleList = this.roleService.getAllRole();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all roles successfully",
                        roleList
                )
        );
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<ResponseDTO<ResRoleDTO>>  getRoleById(@PathVariable Long id) {
        ResRoleDTO role = this.roleService.handleRoleById(id);
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get role successfully",
                        role
                )
        );
    }

    @PostMapping("/roles")
    public ResponseEntity<ResponseDTO<ResRoleDTO>>  createRole(@Valid @RequestBody Role role) {
        ResRoleDTO newRole = this.roleService.handleCreateRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO<>(
                        201,
                        true,
                        null,
                        "Create role successfully",
                        newRole
                )
        );
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<ResponseDTO<ResRoleDTO>>  updateRole(@Valid @RequestBody Role role, @PathVariable Long id) {
        ResRoleDTO updatedRole = this.roleService.handleUpdateRole(role, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Update role successfully",
                        updatedRole
                )
        );
    }

    @PatchMapping("/roles/{id}")
    public ResponseEntity<ResponseDTO>  deleteRole(@PathVariable Long id) {
        this.roleService.handleDeleteRole(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Delete role successfully",
                        null
                )
        );
    }
}
