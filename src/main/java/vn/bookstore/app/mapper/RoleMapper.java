package vn.bookstore.app.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.bookstore.app.dto.request.ReqRoleDTO;
import vn.bookstore.app.dto.request.ReqSeniorityLevelDTO;
import vn.bookstore.app.dto.response.ResRoleDTO;
import vn.bookstore.app.model.Role;
import vn.bookstore.app.model.SeniorityLevel;

@Mapper(componentModel = "spring", uses = SeniorityLevelMapper.class)
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);


    Role convertoRole(ReqRoleDTO reqRoleDTO);

    @Mapping(source = "seniorityLevels", target = "resSeniority", qualifiedByName ="convertToResSeniorityDTO")
    @Mapping(source = "id", target = "idString", qualifiedByName ="formatIdString")
    @Mapping(source = "allowance.id", target = "allowanceId", qualifiedByName ="formatIdAllowance")
    ResRoleDTO convertToResRoleDTO(Role role);

    @Mapping(target = "id", ignore = true) // Không ghi đè ID
    void updateRole(Role updateRole, @MappingTarget Role current);

    @Named("formatIdString")
    static String formatIdString(Long id) {
        return "ROL-" + id;
    }
    @Named("formatIdAllowance")
    static String formatIdAllowance(Long id) {
        return "ALO-" + id;
    }
}
