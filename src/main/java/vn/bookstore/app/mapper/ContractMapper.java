package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import vn.bookstore.app.dto.request.ReqContractDTO;
import vn.bookstore.app.dto.request.ReqContractUserDTO;
import vn.bookstore.app.dto.response.ResContractDTO;
import vn.bookstore.app.model.Contract;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);

    @Mapping(source = "seniorityLevel.levelName", target = "levelName")
    @Mapping(source = "seniorityLevel.salaryCoefficient", target = "salaryCoefficient")
    @Mapping(source = "seniorityLevel.role.name", target = "roleName")
    @Mapping(source = "seniorityLevel.id", target = "seniorityId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "id", target = "idString", qualifiedByName = "formatId")
    ResContractDTO convertToResContractDTO(Contract contract);

    @Named("formatId")
    static String formatId(Long id) {
        return "CTR-" + id;
    }

    @Mapping(source = "id", target = "userId")
    ReqContractDTO convertToReqContractDTO(ReqContractUserDTO reqContract, Long id);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "seniorityId", target = "seniorityLevel.id")
    @Mapping(target = "user.fullName", ignore = true)
    @Mapping(target = "seniorityLevel.levelName", ignore = true)
    @Mapping(target = "seniorityLevel.salaryCoefficient", ignore = true)
    @Mapping(target = "seniorityLevel.role", ignore = true)
    @Mapping(target = "status", ignore = true)
    Contract convertToContract(ReqContractDTO reqContractDTO);
}
