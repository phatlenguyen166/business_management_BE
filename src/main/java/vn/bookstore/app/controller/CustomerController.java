package vn.bookstore.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.bookstore.app.dto.request.ReqCustomerDTO;
import vn.bookstore.app.dto.response.ResCustomerDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "API quản lý khách hàng")

public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/add")
    @Operation(summary = "Thêm khách hàng", description = "Thêm khách hàng")
    public ResponseEntity<ResponseDTO<ResCustomerDTO>> createCustomer(@RequestBody @Valid ReqCustomerDTO customer) {
        ResCustomerDTO responseDTO = customerService.addCustomer(customer);
        return ResponseEntity.ok(ResponseDTO.success(true, "Thêm khách hàng thành công!", responseDTO));
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Sửa khách hàng", description = "Sửa thông tin khách hàng")
    public ResponseEntity<ResponseDTO<ResCustomerDTO>> updateSupplier(@RequestBody @Valid ReqCustomerDTO reqCustomerDTO,
            @PathVariable Long customerId) {
        ResCustomerDTO responseDTO = customerService.updateCustomer(reqCustomerDTO, customerId);
        return ResponseEntity.ok(ResponseDTO.success(true, "Cập nhật khách hàng thành công!", responseDTO));
    }

    @GetMapping("/list")
    @Operation(summary = "Lấy danh sách khách hàng", description = "Lấy danh sách khách hàng")
    public ResponseEntity<ResponseDTO<List<ResCustomerDTO>>> getListCustomers() {
        List<ResCustomerDTO> listCustomers = customerService.getListCustomers();
        return ResponseEntity.ok(ResponseDTO.success(true, "Lấy danh sách khách hàng thành "
                + "công", listCustomers));
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Lấy khách hàng theo ID", description = "Lấy khách hàng theo Id")
    public ResponseEntity<ResponseDTO<ResCustomerDTO>> getCustomerById(@PathVariable Long customerId) {
        ResCustomerDTO resCustomerDTO = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(ResponseDTO.success(true, "Lấy khách hàng thành công", resCustomerDTO));
    }

    @PatchMapping("/{customerId}")
    @Operation(summary = "Xóa khách hàng", description = "Xóa khách hàng")
    public ResponseEntity<ResponseDTO<Void>> disableCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), true, null, "Xóa khách hàng thành công",
                null));
    }
}
