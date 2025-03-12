package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.bookstore.app.dto.request.ReqProductDTO;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.model.Product;

@Mapper(componentModel = "spring", uses = { SupplierMapper.class })
public interface ProductMapper {
    @Mapping(target = "supplier", source = "supplier")
    ResProductDTO toResProductDTO(Product product);

    @Mapping(target = "supplier", ignore = true)
    Product toProduct(ReqProductDTO reqProductDTO);

    @Mapping(target = "supplier", ignore = true)
    void updateProductFromDto(ReqProductDTO reqProductDTO, @MappingTarget Product product);
}
