package vn.bookstore.app.service.impl;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import vn.bookstore.app.dto.request.ReqProductDTO;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.mapper.ProductMapper;
import vn.bookstore.app.model.Product;
import vn.bookstore.app.repository.ProductRepository;
import vn.bookstore.app.service.CloudinaryService;
import vn.bookstore.app.service.ProductService;
import vn.bookstore.app.util.error.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CloudinaryService cloudinaryService;
    
    @Transactional
    @Override
    public ResProductDTO addProduct(ReqProductDTO reqProductDTO, MultipartFile imageFile) throws IOException {
        if (reqProductDTO.getName() == null || reqProductDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sản phẩm không được để trống.");
        }
        
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Ảnh sản phẩm không được để trống.");
        }
        if (productRepository.existsByName(reqProductDTO.getName())) {
            throw new IllegalArgumentException("Sản phẩm với tên '" + reqProductDTO.getName() + "' đã tồn tại.");
        }
        String imageUrl = cloudinaryService.uploadFile(imageFile);
        Product product = productMapper.toProduct(reqProductDTO);
        product.setImage(imageUrl);
        product.setQuantity(0);
        product.setStatus(1);
        log.info("-------> ", product.getImage());
        Product savedProduct = productRepository.save(product);
        
        return productMapper.toResProductDTO(savedProduct);
    }
    
    @Override
    public ResProductDTO updateProduct(ReqProductDTO reqProductDTO, Long productId, MultipartFile newImageFile) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sản phẩm không tồn tại."));
        
        if (newImageFile != null && !newImageFile.isEmpty()) {
            Optional.ofNullable(product.getImage())
                    .filter(imageUrl -> !imageUrl.trim().isEmpty())
                    .ifPresent(imageUrl -> {
                        try {
                            String publicId = cloudinaryService.extractPublicId(imageUrl);
                            cloudinaryService.deleteFile(publicId);
                        } catch (IOException e) {
                            throw new RuntimeException("Lỗi khi xóa ảnh trên Cloudinary: " + e.getMessage());
                        }
                    });
            
            // Upload ảnh mới
            String newImageUrl = cloudinaryService.uploadFile(newImageFile);
            product.setImage(newImageUrl);
        }
        
        Optional.ofNullable(reqProductDTO.getName()).ifPresent(product::setName);
        Optional.ofNullable(reqProductDTO.getPrice()).ifPresent(product::setPrice);
        
        Product updatedProduct = productRepository.save(product);
        
        return productMapper.toResProductDTO(updatedProduct);
    }
    
    
    @Override
    public List<ResProductDTO> getListProducts() {
        return productRepository.findAll().stream().map(productMapper::toResProductDTO).toList();
    }
    
    @Override
    @NonNull
    public ResProductDTO getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::toResProductDTO).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + id));
    }
    
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tìm " +
                "thấy sản phẩm với ID: " + id));
        
        product.setStatus(0);
        productRepository.save(product);
    }
}
