package vn.bookstore.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.bookstore.app.dto.request.ReqProductDTO;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.dto.response.ResResponse;
import vn.bookstore.app.service.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@Validated
@Tag(name="Products")
public class ProductController {
    
    private final ProductService productService;
    
    @PostMapping("/add")
    public ResponseEntity<ResResponse<ResProductDTO>> addProduct(
            @RequestPart("product") String productJson,  // Nhận JSON dạng String
            @RequestPart("file") MultipartFile file) throws IOException {
        
        ObjectMapper objectMapper = new ObjectMapper();
        ReqProductDTO reqProductDTO = objectMapper.readValue(productJson, ReqProductDTO.class);
 
        ResProductDTO newProduct = productService.addProduct(reqProductDTO, file);
        
        ResResponse<ResProductDTO> response = ResResponse.success("Thêm sản phẩm thành công", newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/{productId}")
    public ResponseEntity<ResResponse<ResProductDTO>> updateProduct(
            @PathVariable Long productId,
            @RequestPart("product") String productJson,  // JSON dạng String
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        
        ObjectMapper objectMapper = new ObjectMapper();
        ReqProductDTO reqProductDTO = objectMapper.readValue(productJson, ReqProductDTO.class);
        
        ResProductDTO updatedProduct = productService.updateProduct(reqProductDTO, productId, file);

        ResResponse<ResProductDTO> response = ResResponse.success("Cập nhật sản phẩm thành công", updatedProduct);
        return ResponseEntity.ok(response);
    }
    
    
    @GetMapping("/list")
    public ResponseEntity<ResResponse<List<ResProductDTO>>> getListProducts() {
        List<ResProductDTO> productDTOList = productService.getListProducts();
        ResResponse<List<ResProductDTO>> response = ResResponse.success("Lấy danh sách sản phẩm thành công",
                productDTOList);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<ResResponse<ResProductDTO>> getProductById(@PathVariable Long productId) {
        ResProductDTO productDTO = productService.getProductById(productId);
        ResResponse<ResProductDTO> response = ResResponse.success("Lấy sản phẩm thành công", productDTO);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{productId}")
    public ResponseEntity<ResResponse<Void>> disableProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new ResResponse<>(HttpStatus.OK.value(), null, "Xóa sản phẩm thành công", null));
    }
}
