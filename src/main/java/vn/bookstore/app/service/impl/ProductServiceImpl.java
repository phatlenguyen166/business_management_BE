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

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    
    @Override
    public ResProductDTO addProduct(ResProductDTO resProductDTO) {
        if (productRepository.existsByName(resProductDTO.getName())) {
            throw new IllegalArgumentException("Sản phẩm với tên '" + resProductDTO.getName() + "' đã tồn tại.");
        }
        
        Product product = new Product();
        product.setName(resProductDTO.getName());
        product.setImage(resProductDTO.getImage());
        product.setQuantity(resProductDTO.getQuantity());
        product.setPrice(resProductDTO.getPrice());
        product.setStatus(resProductDTO.getStatus());
        
        // Lưu sản phẩm vào database
        Product savedProduct = productRepository.save(product);
        
        // Chuyển đổi entity thành DTO và trả về
        return productMapper.toResProductDTO(savedProduct);
    }
    
    
    @Override
    public ResProductDTO updateProduct(ResProductDTO resProductDTO, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + productId));
        
        if (productRepository.existsByName(resProductDTO.getName())) {
            throw new IllegalArgumentException("Tên sản phẩm '" + resProductDTO.getName() + "' đã tồn tại.");
        }
        
        
        productMapper.updateProductFromDto(resProductDTO, product);
        Product updatedProduct = productRepository.save(product);
        
        return productMapper.toResProductDTO(updatedProduct);
    }
    
    
    @Override
    public List<ResProductDTO> getListProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResProductDTO)
                .toList();
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
