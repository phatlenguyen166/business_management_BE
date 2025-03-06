package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "fullName")
    ResContractDTO convertToResContractDTO(Contract contract);

    @Mapping(source = "id", target = "userId")
    ReqContractDTO convertToReqContractDTO (ReqContractUserDTO reqContract, Long id);

    Contract convertToContract(ReqContractDTO reqContractDTO);




}
