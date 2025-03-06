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
    ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);

    SeniorityLevel convertoSeniorityLevel(ReqSeniorityLevelDTO reqSeniorityLevelDTO);

    @Named("convertToResSeniorityDTO")
    @Mapping(source = "role.id", target = "roleId")
    ResSeniorityDTO convertToResSeniorityDTO(SeniorityLevel seniorityLevel);

    @Mapping(target = "id", ignore = true) // Không ghi đè ID
    void updateSeniorityLevelFromDTO(ReqSeniorityLevelDTO dto, @MappingTarget SeniorityLevel entity);
}
