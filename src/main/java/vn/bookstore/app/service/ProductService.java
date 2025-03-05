package vn.bookstore.app.service;

import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.response.ResProductDTO;

import java.util.List;

@Service
public interface ProductService {
    List<ResProductDTO> getListProducts();
    
    ResProductDTO getProductById(Long id);
    
    void deleteProduct(Long id);
    
    ResProductDTO updateProduct(ResProductDTO resProductDTO, Long productId);
    
    ResProductDTO addProduct(ResProductDTO resProductDTO); // Thêm phương thức tạo sản phẩm
}
