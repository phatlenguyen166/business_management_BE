package vn.bookstore.app.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import vn.bookstore.app.dto.response.ResRoleDTO;
import vn.bookstore.app.model.Role;

@Mapper(componentModel = "spring", uses = SeniorityLevelMapper.class)
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(source = "seniorityLevels", target = "resSeniority", qualifiedByName ="convertToResSeniorityDTO")
    ResRoleDTO convertToResRoleDTO(Role role);

    @Mapping(target = "id", ignore = true) // Không ghi đè ID
    void updateRole(Role updateRole, @MappingTarget Role current);
}
