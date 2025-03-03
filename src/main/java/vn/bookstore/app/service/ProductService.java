package vn.bookstore.app.service;

import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.response.ResProductDTO;

import java.util.List;

@Service
public interface ProductService {
    List<ResProductDTO> getListProducts();
    
    ResProductDTO getProductById(Long id);
    
    void deleteProduct(Long id);
}