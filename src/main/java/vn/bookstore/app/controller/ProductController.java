package vn.bookstore.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.bookstore.app.dto.request.ReqProductDTO;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@Validated
@Tag(name = "Products", description = "API quản lý sản phẩm")
public class ProductController {
    
    private final ProductService productService;
    
    @PostMapping("/add")
    @Operation(summary = "Thêm", description = "Thêm sản phẩm")
    public ResponseEntity<ResponseDTO<ResProductDTO>> addProduct(
            @RequestPart("product") String productJson,  // Nhận JSON dạng String
            @RequestPart("file") MultipartFile file) throws IOException {
        
        ObjectMapper objectMapper = new ObjectMapper();
        ReqProductDTO reqProductDTO = objectMapper.readValue(productJson, ReqProductDTO.class);
        
        ResProductDTO newProduct = productService.addProduct(reqProductDTO, file);
        
        ResponseDTO<ResProductDTO> response = ResponseDTO.success(true, "Thêm sản phẩm thành công", newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    
    @PutMapping("/{productId}")
    @Operation(summary = "Sửa", description = "Sửa sản phẩm")
    public ResponseEntity<ResponseDTO<ResProductDTO>> updateProduct(
            @PathVariable Long productId,
            @RequestPart("product") String productJson,  // JSON dạng String
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        
        ObjectMapper objectMapper = new ObjectMapper();
        ReqProductDTO reqProductDTO = objectMapper.readValue(productJson, ReqProductDTO.class);
        
        ResProductDTO updatedProduct = productService.updateProduct(reqProductDTO, productId, file);
        
        ResponseDTO<ResProductDTO> response = ResponseDTO.success(true, "Cập nhật sản phẩm thành công", updatedProduct);
        return ResponseEntity.ok(response);
    }
    
    
    @GetMapping("/list")
    @Operation(summary = "Lấy danh sách sản phẩm", description = "Lấy danh sách sản phẩm")
    public ResponseEntity<ResponseDTO<List<ResProductDTO>>> getListProducts() {
        List<ResProductDTO> productDTOList = productService.getListProducts();
        ResponseDTO<List<ResProductDTO>> response = ResponseDTO.success(true, "Lấy danh sách sản phẩm thành công",
                productDTOList);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{productId}")
    @Operation(summary = "Lấy một sản phẩm", description = "Lấy một sản phẩm với ID")
    public ResponseEntity<ResponseDTO<ResProductDTO>> getProductById(@PathVariable Long productId) {
        ResProductDTO productDTO = productService.getProductById(productId);
        ResponseDTO<ResProductDTO> response = ResponseDTO.success(true, "Lấy sản phẩm thành công", productDTO);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{productId}")
    @Operation(summary = "Xóa", description = "Xóa sản phẩm")
    public ResponseEntity<ResponseDTO<Void>> disableProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), true, null, "Xóa sản phẩm thành công", null));
    }
}
