package vn.bookstore.app.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import vn.bookstore.app.dto.request.ReqLeaveReqDTO;
import vn.bookstore.app.dto.response.ResLeaveReqDTO;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.LeaveRequest;
import vn.bookstore.app.model.User;
import vn.bookstore.app.service.impl.ContractServiceImpl;


@Mapper(componentModel = "spring", uses = ContractMapperHelper.class)
public interface LeaveReqMapper {
    LeaveReqMapper INSTANCE = Mappers.getMapper(LeaveReqMapper.class);
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user", target = "roleName", qualifiedByName = "formatRoleName")
    @Mapping(source = "id", target = "idString", qualifiedByName = "formatId")
    ResLeaveReqDTO convertToResLeaveReqDTO(LeaveRequest leaveRequest);
    @Named("formatId")
    static String formatId(Long id) {
        return "LEV-" + id;
    }

    @Named("formatRoleName")
    static String formatRoleName(User user, @Context ContractServiceImpl contractService) {
        return contractService.getActiveRoleName(user);
    }

    LeaveRequest convertToLeaveRequest(ReqLeaveReqDTO  leaveRequestDTO);

    @Mapping(target = "id", ignore = true) // Không ghi đè ID
    void updateLeaveReq(ReqLeaveReqDTO update, @MappingTarget LeaveRequest current);
}
