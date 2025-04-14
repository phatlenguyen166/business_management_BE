package vn.bookstore.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.bookstore.app.dto.request.ReqBillDTO;
import vn.bookstore.app.dto.request.ReqBillDetailDTO;
import vn.bookstore.app.dto.response.CustomerInfoDTO;
import vn.bookstore.app.dto.response.ResBillDTO;
import vn.bookstore.app.mapper.BillMapper;
import vn.bookstore.app.model.Bill;
import vn.bookstore.app.model.BillDetail;
import vn.bookstore.app.model.Customer;
import vn.bookstore.app.model.Product;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.BillDetailRepository;
import vn.bookstore.app.repository.BillRepository;
import vn.bookstore.app.service.BillService;
import vn.bookstore.app.service.CustomerService;
import vn.bookstore.app.service.ProductService;
import vn.bookstore.app.service.UserService;
import vn.bookstore.app.util.error.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final BillDetailRepository billDetailRepository;
    private final BillMapper billMapper;
    private final UserService userService;
    private final ProductService productService;
    private final CustomerService customerService;

    @Override
    @Transactional
    public ResBillDTO createBill(ReqBillDTO request) {
        if (request.getBillDetails() == null || request.getBillDetails().isEmpty()) {
            throw new IllegalArgumentException("Danh sách sản phẩm không được trống");
        }

        log.info("request --------------> : {}", request);
        User user = userService.findUserById(request.getUserId());

        Customer customer = customerService.findById(request.getCustomerId());

        Bill bill = Bill.builder()
                .user(user)
                .totalPrice(BigDecimal.ZERO)
                .customer(customer)
                .address(request.getAddress())
                .build();

        Bill savedBill = billRepository.save(bill);

        List<Long> productIds = request.getBillDetails().stream()
                .map(ReqBillDetailDTO::getProductId)
                .collect(Collectors.toList());

        Map<Long, Product> productMap = new HashMap<>(productService.findAllById(productIds).stream()
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
        productService.saveAll(new ArrayList<>(productMap.values()));

        if (totalPrice.get().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Tổng tiền không hợp lệ");
        }

        savedBill.setTotalPrice(totalPrice.get());
        savedBill.setBillDetails(billDetails);
        billRepository.save(savedBill);

        Customer customerData = savedBill.getCustomer();
        CustomerInfoDTO customerInfo = CustomerInfoDTO.builder()
                .id(customerData.getId())
                .customerName(customerData.getName())
                .customerPhone(customerData.getPhoneNumber())
                .customerAddress(customerData.getAddress())
                .build();

        Integer totalQuantity = billDetails.stream()
                .mapToInt(detail -> detail.getQuantity())
                .sum();

        return ResBillDTO.builder()
                .id(savedBill.getId())
                .idString("ORD" + savedBill.getId()) // Thêm dòng này
                .userId(savedBill.getUser().getId())
                .totalPrice(savedBill.getTotalPrice())
                .totalAmount(totalQuantity) // Gán tổng số lượng sản phẩm
                .customerInfo(customerInfo)
                .billDetails(billDetails.stream()
                        .map(billMapper::toResBillDetail)
                        .collect(Collectors.toList())
                )
                .createdAt(savedBill.getCreatedAt())
                .updatedAt(savedBill.getUpdatedAt())
                .build();
    }

    @Override
    public List<ResBillDTO> getListBills() {
        return billRepository.findAll().stream().map(billMapper::toResBill).toList();
    }

    @Override
    public ResBillDTO getBillById(Long id) {
        return billRepository.findById(id).map(billMapper::toResBill)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiếu nhập với ID: " + id));
    }

}
