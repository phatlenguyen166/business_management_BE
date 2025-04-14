package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import vn.bookstore.app.dto.request.ReqCustomerDTO;
import vn.bookstore.app.dto.response.ResCustomerDTO;
import vn.bookstore.app.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "bills", ignore = true)
    Customer toCustomer(ReqCustomerDTO reqCustomerDTO);

    @Mapping(source = "id", target = "idString", qualifiedByName = "longToString")
    ResCustomerDTO toResCustomerDTO(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "bills", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateCustomerFromDto(ReqCustomerDTO reqCustomerDTO, @MappingTarget Customer customer);

    @org.mapstruct.Named("longToString")
    default String longToString(Long value) {
        return value != null ? "KH-" + value : null;
    }
}
