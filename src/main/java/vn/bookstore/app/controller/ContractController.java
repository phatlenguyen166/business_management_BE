package vn.bookstore.app.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.ReqContractDTO;
import vn.bookstore.app.dto.response.ResContractDTO;
import vn.bookstore.app.dto.response.ResResponse;
import vn.bookstore.app.service.ContractService;
import vn.bookstore.app.util.error.NotFoundException;

import java.util.List;

@RestController
public class ContractController {
    private ContractService contractService;
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/contracts")
    public ResponseEntity<ResContractDTO> createContract(@Valid @RequestBody ReqContractDTO contract) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.contractService.handleCreateContract(contract));
    }

    @GetMapping("/contracts")
    public ResponseEntity<List<ResContractDTO>> getAllContracts() {
        return ResponseEntity.ok().body(this.contractService.handleGetAllContracts());
    }

    @GetMapping("/contracts/{id}")
    public ResponseEntity<ResResponse<ResContractDTO>> getContractById(@PathVariable Long id) throws NotFoundException {
        ResContractDTO resContractDTO = this.contractService.getContractById(id);
        if(resContractDTO == null) {
            throw new NotFoundException("Hợp đồng không tồn tại");
        } else {
            return ResponseEntity.ok().body(
                    new ResResponse<>(
                            200,
                            null,
                            "Get contract successfully",
                            resContractDTO
                    )
            );
        }
    }

    @PutMapping("/contracts/{id}")
    public ResponseEntity<ResResponse<ResContractDTO>> updateContract(@Valid @RequestBody ReqContractDTO contract, @PathVariable Long id) throws NotFoundException {
        if(this.contractService.getContractById(id) == null) {
            throw new NotFoundException("Hợp đồng không tồn tại");
        }
        ResContractDTO updatedContract = this.contractService.handleUpdatedContract(contract, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResResponse<>(
                        201,
                        null,
                        "Create contract successfully",
                        updatedContract
                )
        );
    }

    @PatchMapping("/contracts/{id}")
    public ResponseEntity<ResResponse> deleteContractById(@PathVariable Long id) throws NotFoundException {
        if(this.contractService.getContractById(id) == null) {
            throw new NotFoundException("Hợp đồng không tồn tại");
        }
        this.contractService.handleDeleteContract(id);
        return ResponseEntity.ok().body(
                new ResResponse<>(
                        200,
                        null,
                        "Delete contract successfully",
                        null
                )
        );
    }

}
