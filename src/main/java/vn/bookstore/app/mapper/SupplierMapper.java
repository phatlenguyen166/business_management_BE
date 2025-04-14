package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import vn.bookstore.app.dto.request.ReqSupplierDTO;
import vn.bookstore.app.dto.response.ResSupplierDTO;
import vn.bookstore.app.model.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    ResSupplierDTO toResSupplierDTO(Supplier supplier);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    Supplier toSupplier(ReqSupplierDTO reqSupplierDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateSupplierFromDto(ReqSupplierDTO reqSupplierDTO, @MappingTarget Supplier supplier);
}
