package vn.bookstore.app.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import vn.bookstore.app.dto.request.ReqProductDTO;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "authorId", source = "author.id")
    ResProductDTO toResProductDTO(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "billDetails", ignore = true)
    @Mapping(target = "goodReceiptDetails", ignore = true)
    Product toProduct(ReqProductDTO reqProductDTO);

    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "author", ignore = true)
    @BeanMapping(ignoreByDefault = true)
    void updateProductFromDto(ReqProductDTO reqProductDTO, @MappingTarget Product product);
}
