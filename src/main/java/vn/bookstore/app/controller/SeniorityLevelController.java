package vn.bookstore.app.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.ReqSeniorityLevelDTO;
import vn.bookstore.app.dto.response.ResResponse;
import vn.bookstore.app.model.SeniorityLevel;
import vn.bookstore.app.service.impl.SeniorityLevelServiceImpl;
import vn.bookstore.app.util.error.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SeniorityLevelController {
    private SeniorityLevelServiceImpl seniorityLevelService;
    public SeniorityLevelController(SeniorityLevelServiceImpl seniorityLevelService) {
        this.seniorityLevelService = seniorityLevelService;
    }


    @PostMapping("/seniorityLevels")
    public ResponseEntity<ResResponse<SeniorityLevel>> createSeniorityLevel(@Valid @RequestBody ReqSeniorityLevelDTO reqSeniorityLevel) {
        SeniorityLevel newSeniorityLevel = this.seniorityLevelService.handleCreateSeniorityLevel(reqSeniorityLevel);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResResponse<>(
                        201,
                        null,
                        "Create seniorityLevel successfully",
                        newSeniorityLevel
                )
        );
    }

    @GetMapping("/seniorityLevels")
    public ResponseEntity<ResResponse<List<SeniorityLevel>>> getAllSeniorityLevels() {
        List<SeniorityLevel> seniorityLevels = this.seniorityLevelService.handleGetAllSeniority();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResResponse<>(
                        200,
                        null,
                        "Get all seniorityLevels successfully",
                        seniorityLevels
                )
        );
    }

    @GetMapping("/seniorityLevels/role/{roleId}")
    public ResponseEntity<ResResponse<List<SeniorityLevel>>> getAllSeniorityLevelsByRoleId(@PathVariable Long roleId) {
        List<SeniorityLevel> seniorityLevels = this.seniorityLevelService.handleGetAllSeniorityByRoleId(roleId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResResponse<>(
                        200,
                        null,
                        "Get all seniorityLevels successfully",
                        seniorityLevels
                )
        );
    }

    @GetMapping("/seniorityLevels/{id}")
    public ResponseEntity<ResResponse<SeniorityLevel>> getSeniorityLevelById(@PathVariable Long id) throws NotFoundException {
        SeniorityLevel seniorityLevel = this.seniorityLevelService.handleGetSeniorityById(id);
        if (seniorityLevel == null) {
            throw new NotFoundException("Cấp bậc không tồn tại");
        }
        return ResponseEntity.ok().body(
                new ResResponse<>(
                        200,
                        null,
                        "Get seniorityLevel successfully",
                        seniorityLevel
                )
        );
    }

    @PutMapping("/seniorityLevels/{id}")
    public ResponseEntity<ResResponse<SeniorityLevel>> updateSeniorityLevel(@Valid @RequestBody ReqSeniorityLevelDTO reqSeniorityLevel, @PathVariable Long id) throws NotFoundException {
        if (this.seniorityLevelService.handleGetSeniorityById(id) == null) {
            throw new NotFoundException("Cấp bậc không tồn tại");
        }
        SeniorityLevel updatedSeniorityLevel = this.seniorityLevelService.handleUpdateSeniority(reqSeniorityLevel, id);
        return ResponseEntity.ok().body(
                new ResResponse<>(
                        200,
                        null,
                        "Update seniorityLevel successfully",
                        updatedSeniorityLevel
                )
        );
    }

    @PatchMapping("/seniorityLevels/{id}")
    public ResponseEntity<ResResponse> deleteSeniorityLevel(@PathVariable Long id) throws NotFoundException {
        if (this.seniorityLevelService.handleGetSeniorityById(id) == null) {
            throw new NotFoundException("Cấp bậc không tồn tại");
        }
         this.seniorityLevelService.handleDeleteSeniority(id);
        return ResponseEntity.ok().body(
                new ResResponse<>(
                        200,
                        null,
                        "Delete seniorityLevel successfully",
                        null
                )
        );
    }}
