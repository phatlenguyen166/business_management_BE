package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ResProductDTO toResProductDTO(Product product);
}
