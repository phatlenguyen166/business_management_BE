package vn.bookstore.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.response.ResGoodReceiptDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.GoodReceiptService;
import vn.bookstore.app.dto.request.ReqGoodReceiptDTO;

@RestController
@RequestMapping("/api/v1/good-receipt")
@RequiredArgsConstructor
@Tag(name = "Good Receipts", description = "API quản lý phiếu nhập hàng")

public class GoodReceiptController {
    
    private final GoodReceiptService goodReceiptService;
    
    @Operation(summary = "Tạo phiếu nhập hàng", description = "API này tạo một phiếu nhập hàng mới từ danh sách sản " +
            "phẩm")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<ResGoodReceiptDTO>> createGoodReceipt(@RequestBody @Valid ReqGoodReceiptDTO request) {
        ResGoodReceiptDTO resGoodReceiptDTO = goodReceiptService.createGoodReceipt(request);
        return ResponseEntity.ok(ResponseDTO.success(true, "Nhập hàng thành công!", resGoodReceiptDTO));
    }
    
}
