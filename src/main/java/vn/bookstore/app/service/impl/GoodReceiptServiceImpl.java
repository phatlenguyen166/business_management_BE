package vn.bookstore.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import vn.bookstore.app.service.GoodReceiptService;
import vn.bookstore.app.service.ProductService;
import vn.bookstore.app.service.UserService;
import vn.bookstore.app.util.error.ResourceNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodReceiptServiceImpl implements GoodReceiptService {

    private final GoodReceiptRepository goodReceiptRepository;
    private final GoodReceiptDetailRepository goodReceiptDetailRepository;
    private final GoodReceiptMapper goodReceiptMapper;
    private final ProductService productService;
    private final UserService userService;

    @Transactional
    @Override
    public ResGoodReceiptDTO createGoodReceipt(ReqGoodReceiptDTO request) {
        if (request.getGoodReceiptDetails() == null || request.getGoodReceiptDetails().isEmpty()) {
            throw new IllegalArgumentException("Danh sách sản phẩm nhập hàng không được trống");
        }

        User user = userService.findById(request.getUserId());

        GoodReceipt goodReceipt = GoodReceipt.builder()
                .user(user)
                .totalPrice(BigDecimal.ZERO)
                .build();

        GoodReceipt savedReceipt = goodReceiptRepository.save(goodReceipt);
        final GoodReceipt finalSavedReceipt = savedReceipt;

        List<Long> productIds = request.getGoodReceiptDetails().stream()
                .map(ReqGoodReceiptDetailDTO::getProductId)
                .toList();

        Map<Long, Product> productMap = productService.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);

        List<GoodReceiptDetail> details = request.getGoodReceiptDetails().stream().map(detailDTO -> {
            Product product = productMap.get(detailDTO.getProductId());
            if (product == null) {
                throw new ResourceNotFoundException("Sản phẩm không tồn tại");
            }

            // Kiểm tra xem sản phẩm có nhà cung cấp không
            Supplier supplier = product.getSupplier();
            if (supplier == null) {
                throw new ResourceNotFoundException("Sản phẩm không có thông tin nhà cung cấp");
            }

            // Lấy tỷ lệ phần trăm từ nhà cung cấp của sản phẩm
            double supplierPercentage = supplier.getPercentage();

            BigDecimal inputPrice = product.getPrice().multiply(
                    BigDecimal.ONE.subtract(BigDecimal.valueOf(supplierPercentage)
                            .divide(BigDecimal.valueOf(100))));

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

        productService.saveAll(new ArrayList<>(productMap.values()));

        savedReceipt.setTotalPrice(totalPrice.get());

        goodReceiptRepository.save(savedReceipt);
        savedReceipt.setGoodReceiptDetails(details);

        GoodReceipt finalResult = goodReceiptRepository.findById(savedReceipt.getId()).orElseThrow();

        ResGoodReceiptDTO returnRes = new ResGoodReceiptDTO();
        returnRes.setId(finalResult.getId());
        returnRes.setUserId(finalResult.getUser().getId());
        returnRes.setTotalPrice(finalResult.getTotalPrice());
        returnRes.setGoodReceiptDetails(
                finalResult.getGoodReceiptDetails().stream()
                        .map(goodReceiptMapper::toResGoodReceiptDetail)
                        .toList());

        returnRes.setUpdatedAt(savedReceipt.getUpdatedAt());
        returnRes.setCreatedAt(savedReceipt.getCreatedAt());
        return returnRes;
    }

    @Override
    public List<ResGoodReceiptDTO> getListGoodReceipts() {

        return goodReceiptRepository.findAll().stream().map(goodReceiptMapper::toResGoodReceipt).toList();
    }

    @Override
    public ResGoodReceiptDTO getGoodReceiptById(Long id) {
        return goodReceiptRepository.findById(id).map(goodReceiptMapper::toResGoodReceipt)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiếu nhập với ID: " + id));

    }

}
