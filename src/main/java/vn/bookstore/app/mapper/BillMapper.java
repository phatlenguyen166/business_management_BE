package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.bookstore.app.dto.response.ResBillDetailDTO;
import vn.bookstore.app.model.BillDetail;

@Mapper(componentModel = "spring")
public interface BillMapper {

    @Mapping(source = "product.id", target = "productId")
    ResBillDetailDTO toResBillDetail(BillDetail billDetail);
}
