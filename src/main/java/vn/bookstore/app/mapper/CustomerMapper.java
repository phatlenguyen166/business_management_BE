package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.bookstore.app.dto.request.ReqCustomerDTO;
import vn.bookstore.app.dto.request.ReqSupplierDTO;
import vn.bookstore.app.dto.response.ResCustomerDTO;
import vn.bookstore.app.model.Customer;
import vn.bookstore.app.model.Supplier;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    
    Customer toCustomer(ReqCustomerDTO reqCustomerDTO);
    
    ResCustomerDTO toResCustomerDTO(Customer customer);
    
    void updateCustomerFromDto(ReqCustomerDTO reqCustomerDTO, @MappingTarget Customer customer);
    
}
