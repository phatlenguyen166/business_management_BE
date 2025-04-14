package vn.bookstore.app.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vn.bookstore.app.dto.response.CustomerInfoDTO;
import vn.bookstore.app.dto.response.ResBillDTO;
import vn.bookstore.app.dto.response.ResBillDetailDTO;
import vn.bookstore.app.model.Bill;
import vn.bookstore.app.model.BillDetail;
import vn.bookstore.app.model.Customer;

@Mapper(componentModel = "spring")
public interface BillMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "customerInfo", expression = "java(mapCustomerInfo(bill.getCustomer()))")
    @Mapping(target = "totalAmount", expression = "java(calculateTotalQuantity(bill.getBillDetails()))")
    @Mapping(target = "idString", expression = "java(\"ORD-\" + bill.getId())")
    ResBillDTO toResBill(Bill bill);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ResBillDetailDTO toResBillDetail(BillDetail billDetail);

    default CustomerInfoDTO mapCustomerInfo(Customer customer) {
        if (customer == null) {
            return null;
        }
        return CustomerInfoDTO.builder()
                .id(customer.getId())
                .customerName(customer.getName())
                .customerPhone(customer.getPhoneNumber())
                .customerAddress(customer.getAddress())
                .build();
    }

    default Integer calculateTotalQuantity(List<BillDetail> billDetails) {
        return billDetails.stream()
                .mapToInt(BillDetail::getQuantity)
                .sum();
    }
}
