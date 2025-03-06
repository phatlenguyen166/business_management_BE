package vn.bookstore.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.dto.response.ResResponse;
import vn.bookstore.app.service.ProductService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    
    @GetMapping("/products")
    public ResponseEntity<ResResponse<List<ResProductDTO>>> getListProducts() {
        List<ResProductDTO> productDTOList = productService.getListProducts();
        ResResponse<List<ResProductDTO>> response = ResResponse.success("Lấy danh sách sản phẩm thành công",
                productDTOList);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/product/{productId}")
    public ResponseEntity<ResResponse<ResProductDTO>> getProductById(@PathVariable Long productId) {
        
        ResProductDTO productDTO = productService.getProductById(productId);
        
        ResResponse<ResProductDTO> response = ResResponse.success("Lấy sản phẩm thành công", productDTO);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/product/{productId}")
    public ResponseEntity<ResResponse<Void>> disableProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new ResResponse<>(HttpStatus.OK.value(), null, "Xóa sản phẩm thành công",
                null));
    }
    
    
}