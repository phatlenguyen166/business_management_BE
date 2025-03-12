package vn.bookstore.app.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqGoodReceiptDTO;
import vn.bookstore.app.dto.request.ReqGoodReceiptDetailDTO;
import vn.bookstore.app.dto.response.ResGoodReceiptDTO;
import vn.bookstore.app.mapper.GoodReceiptMapper;
import vn.bookstore.app.model.GoodReceipt;
import vn.bookstore.app.model.GoodReceiptDetail;
import vn.bookstore.app.model.Product;
import vn.bookstore.app.model.Supplier;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.GoodReceiptDetailRepository;
import vn.bookstore.app.repository.GoodReceiptRepository;
import vn.bookstore.app.repository.ProductRepository;
import vn.bookstore.app.repository.SupplierRepository;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.GoodReceiptService;
import vn.bookstore.app.util.error.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodReceiptServiceImpl implements GoodReceiptService {
    
    private final GoodReceiptRepository goodReceiptRepository;
    private final ProductRepository productRepository;
    private final GoodReceiptDetailRepository goodReceiptDetailRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final GoodReceiptMapper goodReceiptMapper;
    
    @Transactional
    @Override
    public ResGoodReceiptDTO createGoodReceipt(ReqGoodReceiptDTO request) {
        if (request.getGoodReceiptDetails() == null || request.getGoodReceiptDetails().isEmpty()) {
            throw new IllegalArgumentException("Danh sách sản phẩm nhập hàng không được trống");
        }
        
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Nhà cung cấp không tồn tại"));
        
        User user = userRepository.findByIdAndStatus(request.getUserId(),1)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));
        
        GoodReceipt goodReceipt = GoodReceipt.builder()
                .supplier(supplier)
                .user(user)
                .totalPrice(BigDecimal.ZERO)
                .build();
        
        GoodReceipt savedReceipt = goodReceiptRepository.save(goodReceipt);
        final GoodReceipt finalSavedReceipt = savedReceipt;
        
        List<Long> productIds = request.getGoodReceiptDetails().stream()
                .map(ReqGoodReceiptDetailDTO::getProductId)
                .toList();
        
        Map<Long, Product> productMap = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
        
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        
        List<GoodReceiptDetail> details = request.getGoodReceiptDetails().stream().map(detailDTO -> {
            Product product = productMap.get(detailDTO.getProductId());
            if (product == null) {
                throw new ResourceNotFoundException("Sản phẩm không tồn tại");
            }
            
            BigDecimal inputPrice = product.getPrice().multiply(
                    BigDecimal.ONE.subtract(BigDecimal.valueOf(supplier.getPercentage()).divide(BigDecimal.valueOf(100)))
            );
            
            BigDecimal itemTotal = inputPrice.multiply(BigDecimal.valueOf(detailDTO.getQuantity()));
            totalPrice.updateAndGet(currentTotal -> currentTotal.add(itemTotal));
            
            return GoodReceiptDetail.builder()
                    .goodReceipt(finalSavedReceipt)
                    .product(product)
                    .quantity(detailDTO.getQuantity())
                    .inputPrice(inputPrice)
                    .build();
        }).toList();
        
        goodReceiptDetailRepository.saveAll(details);
        
        request.getGoodReceiptDetails().forEach(detailDTO -> {
            Product product = productMap.get(detailDTO.getProductId());
            if (product != null) {
                product.setQuantity(product.getQuantity() + detailDTO.getQuantity());
            }
        });
        
        productRepository.saveAll(productMap.values());
        
        savedReceipt.setTotalPrice(totalPrice.get());
        
        goodReceiptRepository.save(savedReceipt);
        savedReceipt.setGoodReceiptDetails(details);
        
        
        GoodReceipt finalResult = goodReceiptRepository.findById(savedReceipt.getId()).orElseThrow();
        
        
        ResGoodReceiptDTO returnRes = new ResGoodReceiptDTO();
        returnRes.setId(finalResult.getId());
        returnRes.setSupplierId(finalResult.getSupplier().getId());
        returnRes.setUserId(finalResult.getUser().getId());
        returnRes.setTotalPrice(finalResult.getTotalPrice());
        returnRes.setGoodReceiptDetails(
                finalResult.getGoodReceiptDetails().stream()
                        .map(goodReceiptMapper::toResGoodReceiptDetail)
                        .toList()
        );
        
        returnRes.setUpdatedAt(savedReceipt.getUpdatedAt());
        returnRes.setCreatedAt(savedReceipt.getCreatedAt());
        return returnRes;
    }
}