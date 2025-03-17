package vn.bookstore.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.bookstore.app.dto.request.ReqGoodReceiptDTO;
import vn.bookstore.app.dto.response.ResGoodReceiptDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.GoodReceiptService;

@RestController
@RequestMapping("/api/v1/good-receipt")
@RequiredArgsConstructor
@Tag(name = "Good Receipts", description = "API quản lý phiếu nhập hàng")

public class GoodReceiptController {

    private final GoodReceiptService goodReceiptService;

    @Operation(summary = "Tạo phiếu nhập hàng", description = "API này tạo một phiếu nhập hàng mới từ danh sách sản phẩm")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<ResGoodReceiptDTO>> createGoodReceipt(
            @RequestBody @Valid ReqGoodReceiptDTO request) {
        ResGoodReceiptDTO resGoodReceiptDTO = goodReceiptService.createGoodReceipt(request);
        return ResponseEntity.ok(ResponseDTO.success(true, "Nhập hàng thành công!", resGoodReceiptDTO));
    }

    @Operation(summary = "Danh sách phiếu nhập", description = "API lấy danh sách phiếu nhập")
    @GetMapping("/list")
    public ResponseEntity<ResponseDTO<List<ResGoodReceiptDTO>>> getListGoodReceipts() {
        List<ResGoodReceiptDTO> lReceiptDTOs = goodReceiptService.getListGoodReceipts();
        return ResponseEntity.ok(ResponseDTO.success(true, "Lấy danh sách phiếu nhập thành công!", lReceiptDTOs));

    }

    @GetMapping("/{goodReceiptId}")
    @Operation(summary = "Lấy một phiếu nhập", description = "Lấy một sản phẩm với ID")
    public ResponseEntity<ResponseDTO<ResGoodReceiptDTO>> getProductById(@PathVariable Long goodReceiptId) {
        ResGoodReceiptDTO receiptDTO = goodReceiptService.getGoodReceiptById(goodReceiptId);
        return ResponseEntity.ok(ResponseDTO.success(true, "Lấy phiếu nhập với ID " + goodReceiptId + " thành công", receiptDTO));
    }
}
