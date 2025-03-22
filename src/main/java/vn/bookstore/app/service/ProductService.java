package vn.bookstore.app.service;

import org.springframework.web.multipart.MultipartFile;
import vn.bookstore.app.dto.request.ReqProductDTO;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.model.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ResProductDTO> getListProducts();
    
    ResProductDTO getProductById(Long id);
    
    void deleteProduct(Long id);
    
    ResProductDTO updateProduct(ReqProductDTO reqProductDTO, Long productId, MultipartFile multipartFile) throws IOException;
    
    ResProductDTO addProduct(ReqProductDTO reqProductDTO, MultipartFile multipartFile) throws IOException;

    List<Product> findAllById(List<Long> ids);

    void saveAll(List<Product> products);
}
