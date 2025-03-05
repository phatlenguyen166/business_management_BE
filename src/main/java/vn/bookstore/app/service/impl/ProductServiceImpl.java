package vn.bookstore.app.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqProductDTO;
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
    public ResProductDTO addProduct(ReqProductDTO reqProductDTO) {
        if (productRepository.existsByName(reqProductDTO.getName())) {
            throw new IllegalArgumentException("Sản phẩm với tên '" + reqProductDTO.getName() + "' đã tồn tại.");
        }
        Product product = productMapper.toProduct(reqProductDTO);
        // Lưu sản phẩm vào database
        Product savedProduct = productRepository.save(product);
        
        // Chuyển đổi entity thành DTO và trả về
        return productMapper.toResProductDTO(savedProduct);
    }
    
    
    @Override
    public ResProductDTO updateProduct(ReqProductDTO reqProductDTO, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + productId));
        
        // Kiểm tra nếu tên sản phẩm đã tồn tại nhưng không phải của sản phẩm đang cập nhật
        if (productRepository.existsByName(reqProductDTO.getName()) && !product.getName().equals(reqProductDTO.getName())) {
            throw new IllegalArgumentException("Tên sản phẩm '" + reqProductDTO.getName() + "' đã tồn tại.");
        }
        
        // Cập nhật thông tin sản phẩm từ DTO
        productMapper.updateProductFromDto(reqProductDTO, product);
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
