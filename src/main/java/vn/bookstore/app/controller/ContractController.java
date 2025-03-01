package vn.bookstore.app.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.bookstore.app.dto.request.ReqContractDTO;
import vn.bookstore.app.dto.response.ResContractDTO;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.service.ContractService;

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

}
