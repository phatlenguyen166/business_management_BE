package vn.bookstore.app.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqBillDTO;
import vn.bookstore.app.dto.request.ReqBillDetailDTO;
import vn.bookstore.app.dto.response.ResBillDTO;
import vn.bookstore.app.mapper.BillMapper;
import vn.bookstore.app.model.*;
import vn.bookstore.app.repository.BillDetailRepository;
import vn.bookstore.app.repository.BillRepository;
import vn.bookstore.app.repository.ProductRepository;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.BillService;
import vn.bookstore.app.util.error.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final UserRepository userRepository;
    private final BillRepository billRepository;
    private final ProductRepository productRepository;
    private final BillDetailRepository billDetailRepository;
    private final BillMapper billMapper;

    @Override
    @Transactional
    public ResBillDTO createBill(ReqBillDTO request) {
        if (request.getBillDetails() == null || request.getBillDetails().isEmpty()) {
            throw new IllegalArgumentException("Danh sách sản phẩm không được trống");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));

        Bill bill = Bill.builder()
                .user(user)
                .totalPrice(BigDecimal.ZERO)
                .build();

        Bill savedBill = billRepository.save(bill);

        List<Long> productIds = request.getBillDetails().stream()
                .map(ReqBillDetailDTO::getProductId)
                .toList();

        Map<Long, Product> productMap = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);

        List<BillDetail> billDetails = request.getBillDetails().stream()
                .map(details -> {
                    Product product = productMap.get(details.getProductId());
                    if (product == null) {
                        throw new ResourceNotFoundException("Sản phẩm không tồn tại");
                    }

                    if (product.getQuantity() < details.getQuantity()) {
                        throw new IllegalArgumentException("Sản phẩm " + product.getId() + " không đủ số lượng trong kho");
                    }

                    BigDecimal price = product.getPrice();
                    BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(details.getQuantity()));

                    totalPrice.updateAndGet(currentTotal -> currentTotal.add(itemTotal));

                    product.setQuantity(product.getQuantity() - details.getQuantity());

                    return BillDetail.builder()
                            .bill(savedBill)
                            .product(product)
                            .quantity(details.getQuantity())
                            .subPrice(price)
                            .build();
                }).toList();

        billDetailRepository.saveAll(billDetails);
        productRepository.saveAll(productMap.values());

        savedBill.setTotalPrice(totalPrice.get());
        savedBill.setBillDetails(billDetails);
        billRepository.save(savedBill);

        return ResBillDTO.builder()
                .id(savedBill.getId())
                .userId(savedBill.getUser().getId())
                .totalPrice(savedBill.getTotalPrice())
                .billDetails(savedBill.getBillDetails().stream()
                        .map(billMapper::toResBillDetail)
                        .toList()
                )
                .createdAt(savedBill.getCreatedAt())
                .updatedAt(savedBill.getUpdatedAt())
                .build();
    }
}
