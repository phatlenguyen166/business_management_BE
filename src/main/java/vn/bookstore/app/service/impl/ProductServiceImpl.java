package vn.bookstore.app.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.mapper.ProductMapper;
import vn.bookstore.app.model.Product;
import vn.bookstore.app.repository.ProductRepository;
import vn.bookstore.app.service.ProductService;
import vn.bookstore.app.util.error.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    
    private final ProductMapper productMapper;
    
    @Override
    public List<ResProductDTO> getListProducts() {
        return productRepository.findAll().stream().map(productMapper::toResProductDTO).toList();
    }
    
    @Override
    @NonNull
    public ResProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toResProductDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + id));
    }
    
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + id));
        
        product.setStatus(0);
        productRepository.save(product);
    }
}