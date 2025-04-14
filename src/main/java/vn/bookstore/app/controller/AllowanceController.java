package vn.bookstore.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.bookstore.app.dto.response.ResAllowanceDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.impl.AllowanceServiceImpl;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AllowanceController {

    private final AllowanceServiceImpl allowanceService;

    @GetMapping("/allowances")
    public ResponseEntity<ResponseDTO<List<ResAllowanceDTO>>> getAllAllowances() {
        List<ResAllowanceDTO> resAllowanceDTOS = this.allowanceService.handleGetAll();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        200,
                        true,
                        null,
                        "Get all allowances successfully",
                        resAllowanceDTOS
                )
        );
    }
}
