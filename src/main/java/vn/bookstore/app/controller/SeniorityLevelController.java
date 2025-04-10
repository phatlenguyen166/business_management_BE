package vn.bookstore.app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.ReqSeniorityLevelDTO;
import vn.bookstore.app.dto.response.ResSeniorityDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.impl.SeniorityLevelServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Seniority Levels")
public class SeniorityLevelController {
    private final SeniorityLevelServiceImpl seniorityLevelService;
    
    public SeniorityLevelController(SeniorityLevelServiceImpl seniorityLevelService) {
        this.seniorityLevelService = seniorityLevelService;
    }
    
    
    @PostMapping("/seniorityLevels")
    public ResponseEntity<ResponseDTO<ResSeniorityDTO>> createSeniorityLevel(@Valid @RequestBody ReqSeniorityLevelDTO reqSeniorityLevel) {
        ResSeniorityDTO newSeniorityLevel = this.seniorityLevelService.handleCreateSeniorityLevel(reqSeniorityLevel);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO<>(
                        201,
                        true,
                        null,
                        "Create seniorityLevel successfully",
                        newSeniorityLevel
                )
        );
    }
    
    @GetMapping("/seniorityLevels")
    public ResponseEntity<ResponseDTO<List<ResSeniorityDTO>>> getAllSeniorityLevels() {
        List<ResSeniorityDTO> seniorityLevels = this.seniorityLevelService.handleGetAllSeniority();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all seniorityLevels successfully",
                        seniorityLevels
                )
        );
    }
    
    @GetMapping("/seniorityLevels/role/{roleId}")
    public ResponseEntity<ResponseDTO<List<ResSeniorityDTO>>> getAllSeniorityLevelsByRoleId(@PathVariable Long roleId) {
        List<ResSeniorityDTO> seniorityLevels = this.seniorityLevelService.handleGetAllSeniorityByRoleId(roleId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all seniorityLevels successfully",
                        seniorityLevels
                )
        );
    }
    
    @GetMapping("/seniorityLevels/{id}")
    public ResponseEntity<ResponseDTO<ResSeniorityDTO>> getSeniorityLevelById(@PathVariable Long id)  {
        ResSeniorityDTO seniorityLevel = this.seniorityLevelService.handleGetSeniorityById(id);
        return ResponseEntity.ok().body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get seniorityLevel successfully",
                        seniorityLevel
                )
        );
    }
    
    @PutMapping("/seniorityLevels/{id}")
    public ResponseEntity<ResponseDTO<ResSeniorityDTO>> updateSeniorityLevel(@Valid @RequestBody ReqSeniorityLevelDTO reqSeniorityLevel, @PathVariable Long id) {
        ResSeniorityDTO updatedSeniorityLevel = this.seniorityLevelService.handleUpdateSeniority(reqSeniorityLevel, id);
        return ResponseEntity.ok().body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Update seniorityLevel successfully",
                        updatedSeniorityLevel
                )
        );
    }
    
    @PatchMapping("/seniorityLevels/{id}")
    public ResponseEntity<ResponseDTO> deleteSeniorityLevel(@PathVariable Long id) {
        this.seniorityLevelService.handleDeleteSeniority(id);
        return ResponseEntity.ok().body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Delete seniorityLevel successfully",
                        null
                )
        );
    }

    @PatchMapping("/seniorityLevels/accept/{id}")
    public ResponseEntity<ResponseDTO> acceptSeniorityLevel(@PathVariable Long id)  {
        this.seniorityLevelService.handleAcceptSeniority(id);
        return ResponseEntity.ok().body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Accept seniorityLevel successfully",
                        null
                )
        );
    }
}
