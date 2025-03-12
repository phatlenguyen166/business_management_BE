package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.bookstore.app.dto.response.ResGoodReceiptDTO;
import vn.bookstore.app.dto.response.ResGoodReceiptDetailDTO;
import vn.bookstore.app.model.GoodReceipt;
import vn.bookstore.app.model.GoodReceiptDetail;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GoodReceiptMapper {
    GoodReceiptMapper INSTANCE = Mappers.getMapper(GoodReceiptMapper.class);

    @Mapping(source = "user.id", target = "userId")
    ResGoodReceiptDTO toResGoodReceipt(GoodReceipt goodReceipt);

    @Mapping(source = "product.id", target = "productId")
    ResGoodReceiptDetailDTO toResGoodReceiptDetail(GoodReceiptDetail goodReceiptDetail);

    default List<ResGoodReceiptDetailDTO> mapGoodReceiptDetails(List<GoodReceiptDetail> details) {
        if (details == null) {
            return null;
        }
        return details.stream().map(detail -> ResGoodReceiptDetailDTO.builder()
                .productId(detail.getProduct().getId())
                .quantity(detail.getQuantity())
                .inputPrice(detail.getInputPrice())
                .build()).collect(Collectors.toList());
    }
}
