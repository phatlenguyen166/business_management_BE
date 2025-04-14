package vn.bookstore.app.mapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import vn.bookstore.app.dto.response.ResAllowanceDTO;
import vn.bookstore.app.model.Allowance;
import vn.bookstore.app.model.Role;

@Mapper(componentModel = "spring", uses = {PayrollMapperHelper.class})
public interface AllowanceMapper {

    AllowanceMapper INSTANCE = Mappers.getMapper(AllowanceMapper.class);

    @Mapping(source = "id", target = "idString", qualifiedByName = "formatId")
    @Mapping(source = "allowance", target = "allowance", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "roles", target = "roleName", qualifiedByName = "mapRolesToIds")
    ResAllowanceDTO convertToResAllowanceDTO(Allowance allowance);

    @Named("formatId")
    static String formatId(Long id) {
        return "AL-" + id;
    }

    @Named("mapRolesToIds")
    static java.util.List<String> mapRolesToIds(java.util.List<Role> roles) {
        if (roles == null) {
            return new ArrayList<>();
        }
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }

}
