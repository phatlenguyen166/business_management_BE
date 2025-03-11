package vn.bookstore.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.request.ReqSupplierDTO;
import vn.bookstore.app.dto.response.ResSupplierDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
@Tag(name = "Suppliers", description = "API quản lý nhà cung cấp")
public class SupplierController {
    
    private final SupplierService supplierService;
    
    @PostMapping("/add")
    @Operation(summary = "Thêm nhà cung cấp", description = "Thêm nhà cung cấp")
    public ResponseEntity<ResponseDTO<ResSupplierDTO>> createSupplier(@RequestBody @Valid ReqSupplierDTO supplierDTO) {
        ResSupplierDTO responseDTO = supplierService.addSupplier(supplierDTO);
        return ResponseEntity.ok(ResponseDTO.success(true, "Thêm nhà cung cấp thành công!", responseDTO));
    }
    
    @PutMapping("/{supplierId}")
    @Operation(summary = "Sửa nhà cung cấp", description = "Sửa thông tin nhà cung cấp")
    public ResponseEntity<ResponseDTO<ResSupplierDTO>> updateSupplier(@RequestBody @Valid ReqSupplierDTO supplierDTO,
                                                                      @PathVariable Long supplierId) {
        ResSupplierDTO responseDTO = supplierService.updateSupplier(supplierDTO, supplierId);
        return ResponseEntity.ok(ResponseDTO.success(true, "Cập nhật nhà cung cấp thành công!", responseDTO));
    }
    
    @GetMapping("/list")
    @Operation(summary = "Lấy danh sách nhà cugn cấp", description = "Lấy danh sách nhà cung cấp")
    public ResponseEntity<ResponseDTO<List<ResSupplierDTO>>> getListSuppliers() {
        List<ResSupplierDTO> listSuppliers = supplierService.getListSuppliers();
        return ResponseEntity.ok(ResponseDTO.success(true, "Lấy danh sách nhà cung cấp thành " +
                "công", listSuppliers));
    }
    
    @GetMapping("/{supplierId}")
    @Operation(summary = "Lấy nhà cung cấp theo ID", description = "Lấy nhà cung cấp theo Id")
    public ResponseEntity<ResponseDTO<ResSupplierDTO>> getSupplierById(@PathVariable Long supplierId) {
        ResSupplierDTO resSupplierDTO = supplierService.getSupplierById(supplierId);
        return ResponseEntity.ok(ResponseDTO.success(true, "Lấy nhà cung cấp thành công", resSupplierDTO));
    }
    
    @PatchMapping("/{supplierId}")
    @Operation(summary = "Xóa nhà cung cấp", description = "Xóa nhà cung cấp")
    public ResponseEntity<ResponseDTO<Void>> disableSupplier(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), true, null, "Xóa nhà cung cấp thành công",
                null));
    }
}
