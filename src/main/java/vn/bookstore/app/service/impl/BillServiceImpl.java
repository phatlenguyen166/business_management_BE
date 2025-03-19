package vn.bookstore.app.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqBillDTO;
import vn.bookstore.app.dto.request.ReqBillDetailDTO;
import vn.bookstore.app.dto.response.ResBillDTO;
import vn.bookstore.app.mapper.BillMapper;
import vn.bookstore.app.model.*;
import vn.bookstore.app.repository.*;
import vn.bookstore.app.service.BillService;
import vn.bookstore.app.util.error.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {

    private final UserRepository userRepository;
    private final BillRepository billRepository;
    private final ProductRepository productRepository;
    private final BillDetailRepository billDetailRepository;
    private final BillMapper billMapper;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public ResBillDTO createBill(ReqBillDTO request) {
        if (request.getBillDetails() == null || request.getBillDetails().isEmpty()) {
            throw new IllegalArgumentException("Danh sách sản phẩm không được trống");
        }

        log.info("request --------------> : {}", request);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại"));

        Bill bill = Bill.builder()
                .user(user)
                .totalPrice(BigDecimal.ZERO)
                .customer(customer)
                .build();

        Bill savedBill = billRepository.save(bill);

        List<Long> productIds = request.getBillDetails().stream()
                .map(ReqBillDetailDTO::getProductId)
                .collect(Collectors.toList());

        Map<Long, Product> productMap = new HashMap<>(productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, product -> product)));

        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);

        List<BillDetail> billDetails = new ArrayList<>();

        for (ReqBillDetailDTO details : request.getBillDetails()) {
            Product product = productMap.get(details.getProductId());
            if (product == null) {
                throw new ResourceNotFoundException("Sản phẩm không tồn tại");
            }

            if (product.getQuantity() < details.getQuantity()) {
                throw new IllegalArgumentException("Sản phẩm " + product.getId() + " không đủ số lượng trong kho");
            }

            BigDecimal price = product.getPrice();
            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Giá sản phẩm không hợp lệ");
            }
            if (details.getQuantity() <= 0) {
                throw new IllegalArgumentException("Số lượng sản phẩm phải lớn hơn 0");
            }

            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(details.getQuantity()));
            totalPrice.updateAndGet(currentTotal -> currentTotal.add(itemTotal));

            product.setQuantity(product.getQuantity() - details.getQuantity());

            BillDetail billDetail = BillDetail.builder()
                    .bill(savedBill)
                    .product(product)
                    .quantity(details.getQuantity())
                    .subPrice(price)
                    .build();

            billDetails.add(billDetail);
        }

        billDetailRepository.saveAll(billDetails);
        productRepository.saveAll(productMap.values());

        if (totalPrice.get().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Tổng tiền không hợp lệ");
        }

        savedBill.setTotalPrice(totalPrice.get());
        savedBill.setBillDetails(billDetails);
        billRepository.save(savedBill);

        return ResBillDTO.builder()
                .id(savedBill.getId())
                .userId(savedBill.getUser().getId())
                .totalPrice(savedBill.getTotalPrice())
                .customerId(savedBill.getCustomer().getId())
                .address(savedBill.getAddress())
                .billDetails(billDetails.stream()
                        .map(billMapper::toResBillDetail)
                        .collect(Collectors.toList())
                )
                .createdAt(savedBill.getCreatedAt())
                .updatedAt(savedBill.getUpdatedAt())
                .build();
    }

}
