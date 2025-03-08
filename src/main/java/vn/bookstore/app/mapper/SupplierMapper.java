package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.bookstore.app.dto.request.ReqProductDTO;
import vn.bookstore.app.dto.request.ReqSupplierDTO;
import vn.bookstore.app.dto.response.ResSupplierDTO;
import vn.bookstore.app.model.Product;
import vn.bookstore.app.model.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    ResSupplierDTO toResSupplierDTO(Supplier supplier);
    
    Supplier toSupplier(ReqSupplierDTO reqSupplierDTO);
    
    void updateSupplierFromDto(ReqSupplierDTO reqSupplierDTO, @MappingTarget Supplier supplier);
}
