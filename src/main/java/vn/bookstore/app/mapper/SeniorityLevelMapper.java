package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import vn.bookstore.app.dto.request.ReqSeniorityLevelDTO;
import vn.bookstore.app.dto.response.ResSeniorityDTO;
import vn.bookstore.app.model.SeniorityLevel;

@Mapper(componentModel = "spring")
public interface SeniorityLevelMapper {

    SeniorityLevelMapper INSTANCE = Mappers.getMapper(SeniorityLevelMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    @Mapping(target = "role", ignore = true)
    SeniorityLevel convertoSeniorityLevel(ReqSeniorityLevelDTO reqSeniorityLevelDTO);

    @Named("convertToResSeniorityDTO")
    @Mapping(source = "role.id", target = "roleId", qualifiedByName = "formatIdRole")
    @Mapping(source = "id", target = "idString", qualifiedByName = "formatId")
    ResSeniorityDTO convertToResSeniorityDTO(SeniorityLevel seniorityLevel);

    @Mapping(target = "id", ignore = true) // Không ghi đè ID
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateSeniorityLevelFromDTO(ReqSeniorityLevelDTO dto, @MappingTarget SeniorityLevel entity);

    @Named("formatId")
    static String formatId(Long id) {
        return "SEL-" + id;
    }

    @Named("formatIdRole")
    static String formatIdRole(Long id) {
        return "ROL-" + id;
    }
}
