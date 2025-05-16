package vn.bookstore.app.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.bookstore.app.dto.request.ReqProductDTO;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.mapper.ProductMapper;
import vn.bookstore.app.model.Author;
import vn.bookstore.app.model.Category;
import vn.bookstore.app.model.Product;
import vn.bookstore.app.model.Supplier;
import vn.bookstore.app.repository.AuthorRepository;
import vn.bookstore.app.repository.CategoryRepository;
import vn.bookstore.app.repository.ProductRepository;
import vn.bookstore.app.repository.SupplierRepository;
import vn.bookstore.app.service.CloudinaryService;
import vn.bookstore.app.service.ProductService;
import vn.bookstore.app.util.error.ExistingIdException;
import vn.bookstore.app.util.error.InvalidRequestException;
import vn.bookstore.app.util.error.ResourceNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CloudinaryService cloudinaryService;
    private final SupplierRepository supplierRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

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
            throw new InvalidRequestException("Sản phẩm với tên '" + reqProductDTO.getName() + "' đã tồn tại.");
        }

        // Get supplier
        Supplier supplier = supplierRepository.findById(reqProductDTO.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException(
                "Không tìm thấy nhà cung cấp với ID: " + reqProductDTO.getSupplierId()));

        // Get author if provided
        Author author = null;
        if (reqProductDTO.getAuthorId() != null) {
            author = authorRepository.findById(reqProductDTO.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                    "Không tìm thấy tác giả với ID: " + reqProductDTO.getAuthorId()));
        }

        // Get category if provided
        Category category = null;
        if (reqProductDTO.getCategoryId() != null) {
            category = categoryRepository.findById(reqProductDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                    "Không tìm thấy danh mục với ID: " + reqProductDTO.getCategoryId()));
        }

        String imageUrl = cloudinaryService.uploadFile(imageFile);
        Product product = productMapper.toProduct(reqProductDTO);
        product.setImage(imageUrl);
        product.setQuantity(0);
        product.setStatus(1);
        product.setSupplier(supplier);
        product.setAuthor(author);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResProductDTO(savedProduct);
    }

    @Override
    public List<Product> findAllById(List<Long> ids) {
        return productRepository.findAllById(ids);
    }

    @Override
    public void saveAll(List<Product> products) {
        productRepository.saveAll(products);
    }

    @Override
    public ResProductDTO updateProduct(ReqProductDTO reqProductDTO, Long productId, MultipartFile newImageFile)
            throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại."));

        if (reqProductDTO.getName() != null && !reqProductDTO.getName().equals(product.getName())
                && productRepository.existsByName(reqProductDTO.getName())) {
            throw new ExistingIdException("Tên sản phẩm đã tồn tại!");
        }

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
            String newImageUrl = cloudinaryService.uploadFile(newImageFile);
            product.setImage(newImageUrl);
        }

        Optional.ofNullable(reqProductDTO.getName()).ifPresent(product::setName);
        Optional.ofNullable(reqProductDTO.getPrice()).ifPresent(product::setPrice);

        // Update supplier if provided
        if (reqProductDTO.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(reqProductDTO.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                    "Không tìm thấy nhà cung cấp với ID: " + reqProductDTO.getSupplierId()));
            product.setSupplier(supplier);
        }

        // Update author if provided
        if (reqProductDTO.getAuthorId() != null) {
            Author author = authorRepository.findById(reqProductDTO.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                    "Không tìm thấy tác giả với ID: " + reqProductDTO.getAuthorId()));
            product.setAuthor(author);
        }

        // Update category if provided
        if (reqProductDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(reqProductDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                    "Không tìm thấy danh mục với ID: " + reqProductDTO.getCategoryId()));
            product.setCategory(category);
        }

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
        return productRepository.findById(id).map(productMapper::toResProductDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + id));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tìm "
                + "thấy sản phẩm với ID: " + id));

        product.setStatus(0);
        productRepository.save(product);
    }
}
