package vn.bookstore.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.dto.response.RestResponse;
import vn.bookstore.app.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    
    private final ProductService productService;
    
    @PostMapping("/add")
    public ResponseEntity<RestResponse<ResProductDTO>> addProduct(@RequestBody ResProductDTO resProductDTO) {
        ResProductDTO newProduct = productService.addProduct(resProductDTO);
        RestResponse<ResProductDTO> response = RestResponse.success("Thêm sản phẩm thành công", newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/{productId}")
    public ResponseEntity<RestResponse<ResProductDTO>> updateProduct(@PathVariable Long productId,
                                                                     @RequestBody ResProductDTO resProductDTO) {
        ResProductDTO updatedProduct = productService.updateProduct(resProductDTO, productId);
        RestResponse<ResProductDTO> response = RestResponse.success("Cập nhật sản phẩm thành công", updatedProduct);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/list")
    public ResponseEntity<RestResponse<List<ResProductDTO>>> getListProducts() {
        List<ResProductDTO> productDTOList = productService.getListProducts();
        RestResponse<List<ResProductDTO>> response = RestResponse.success("Lấy danh sách sản phẩm thành công",
                productDTOList);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<RestResponse<ResProductDTO>> getProductById(@PathVariable Long productId) {
        ResProductDTO productDTO = productService.getProductById(productId);
        RestResponse<ResProductDTO> response = RestResponse.success("Lấy sản phẩm thành công", productDTO);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{productId}")
    public ResponseEntity<RestResponse<Void>> disableProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new RestResponse<>(HttpStatus.OK.value(), null, "Xóa sản phẩm thành công", null));
    }
}
