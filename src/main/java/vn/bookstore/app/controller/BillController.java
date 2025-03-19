package vn.bookstore.app.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.bookstore.app.dto.request.ReqBillDTO;
import vn.bookstore.app.dto.response.ResBillDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.BillService;

@RestController
@RequestMapping("/api/v1/bill")
@RequiredArgsConstructor
@Tag(name = "Bills", description = "API quản lý xuất hàng")
public class BillController {

    private final BillService billService;

    @Operation(summary = "Tạo phiếu xuất hàng", description = "API này tạo một phiếu xuất hàng")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<ResBillDTO>> createBill(
            @RequestBody @Valid ReqBillDTO request) {
        ResBillDTO resBillDTO = billService.createBill(request);
        return ResponseEntity.ok(ResponseDTO.success(true, "Nhập hàng thành công!", resBillDTO));
    }
}
