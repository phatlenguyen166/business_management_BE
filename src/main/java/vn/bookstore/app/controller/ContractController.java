package vn.bookstore.app.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.ReqContractDTO;
import vn.bookstore.app.dto.response.ResContractDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.ContractService;
import vn.bookstore.app.util.error.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")

public class ContractController {
    private ContractService contractService;
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/contracts")
    public ResponseEntity<ResponseDTO<ResContractDTO>> createContract(@Valid @RequestBody ReqContractDTO contract) {
        ResContractDTO newContract = this.contractService.handleCreateContract(contract);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Create contract successfully",
                        newContract
                )
        );
    }

    @GetMapping("/contracts")
    public ResponseEntity<ResponseDTO<List<ResContractDTO>>> getAllContracts() {
        List<ResContractDTO> list = this.contractService.handleGetAllContracts();
        return ResponseEntity.ok().body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get contract successfully",
                        list
                )
        );
    }

    @GetMapping("/contracts/{id}")
    public ResponseEntity<ResponseDTO<ResContractDTO>> getContractById(@PathVariable Long id) throws NotFoundException {
        ResContractDTO resContractDTO = this.contractService.getContractById(id);
        if(resContractDTO == null) {
            throw new NotFoundException("Hợp đồng không tồn tại");
        } else {
            return ResponseEntity.ok().body(
                    new ResponseDTO<>(
                            200,
                            true,
                            null,
                            "Get contract successfully",
                            resContractDTO
                    )
            );
        }
    }

    @PutMapping("/contracts/{id}")
    public ResponseEntity<ResponseDTO<ResContractDTO>> updateContract(@Valid @RequestBody ReqContractDTO contract, @PathVariable Long id) throws NotFoundException {
        if(this.contractService.getContractById(id) == null) {
            throw new NotFoundException("Hợp đồng không tồn tại");
        }
        ResContractDTO updatedContract = this.contractService.handleUpdatedContract(contract, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO<>(
                        201,
                        true,
                        null,
                        "Create contract successfully",
                        updatedContract
                )
        );
    }

    @PatchMapping("/contracts/{id}")
    public ResponseEntity<ResponseDTO> deleteContractById(@PathVariable Long id) throws NotFoundException {
        if(this.contractService.getContractById(id) == null) {
            throw new NotFoundException("Hợp đồng không tồn tại");
        }
        this.contractService.handleDeleteContract(id);
        return ResponseEntity.ok().body(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Delete contract successfully",
                        null
                )
        );
    }

}
