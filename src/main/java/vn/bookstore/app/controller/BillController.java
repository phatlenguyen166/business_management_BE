package vn.bookstore.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.ReqBillDTO;
import vn.bookstore.app.dto.response.ResBillDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.BillService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bill")
@RequiredArgsConstructor
@Tag(name = "Bills", description = "API quản lý xuất hàng")
public class BillController {

    private final BillService billService;

    @Operation(summary = "Tạo phiếu xuất hàng", description = "API này tạo một phiếu xuất hàng")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<ResBillDTO>> createBill(@RequestBody @Valid ReqBillDTO request) {
        ResBillDTO resBillDTO = billService.createBill(request);
        return ResponseEntity.ok(ResponseDTO.success(true, "Tạo phiếu xuất hàng thành công!", resBillDTO));
    }

    @Operation(summary = "Danh sách phiếu xuất hàng", description = "API lấy danh sách phiếu xuất hàng")
    @GetMapping("/list")
    public ResponseEntity<ResponseDTO<List<ResBillDTO>>> getListBills() {
        List<ResBillDTO> bills = billService.getListBills();
        return ResponseEntity.ok(ResponseDTO.success(true, "Lấy danh sách phiếu xuất hàng thành công!", bills));
    }

    @Operation(summary = "Lấy thông tin phiếu xuất hàng", description = "API lấy chi tiết một phiếu xuất hàng theo ID")
    @GetMapping("/{billId}")
    public ResponseEntity<ResponseDTO<ResBillDTO>> getBillById(@PathVariable Long billId) {
        ResBillDTO bill = billService.getBillById(billId);
        return ResponseEntity.ok(ResponseDTO.success(true, "Lấy phiếu xuất hàng thành công!", bill));
    }
}
