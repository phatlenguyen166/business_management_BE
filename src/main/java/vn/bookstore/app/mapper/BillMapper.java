package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.bookstore.app.dto.response.ResBillDTO;
import vn.bookstore.app.dto.response.ResBillDetailDTO;
import vn.bookstore.app.model.Bill;
import vn.bookstore.app.model.BillDetail;

@Mapper(componentModel = "spring")
public interface BillMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "customer.id", target = "customerId")
    ResBillDTO toResBill(Bill bill);

    @Mapping(source = "product.id", target = "productId")
    ResBillDetailDTO toResBillDetail(BillDetail billDetail);
}
