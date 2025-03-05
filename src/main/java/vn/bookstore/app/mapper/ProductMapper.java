package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.bookstore.app.dto.request.ReqProductDTO;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ResProductDTO toResProductDTO(Product product);
    
    Product toProduct(ReqProductDTO reqProductDTO);
    
    void updateProductFromDto(ReqProductDTO reqProductDTO, @MappingTarget Product product);
}
