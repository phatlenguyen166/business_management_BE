package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.bookstore.app.dto.request.ReqLeaveReqDTO;
import vn.bookstore.app.dto.response.ResLeaveReqDTO;
import vn.bookstore.app.model.LeaveRequest;
import vn.bookstore.app.model.Role;

@Mapper(componentModel = "spring")
public interface LeaveReqMapper {
    LeaveReqMapper INSTANCE = Mappers.getMapper(LeaveReqMapper.class);
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "id", target = "idString", qualifiedByName = "formatId")
    ResLeaveReqDTO convertToResLeaveReqDTO(LeaveRequest leaveRequest);
    @Named("formatId")
    static String formatId(Long id) {
        return "LEV-" + id;
    }

    LeaveRequest convertToLeaveRequest(ReqLeaveReqDTO  leaveRequestDTO);

    @Mapping(target = "id", ignore = true) // Không ghi đè ID
    void updateLeaveReq(ReqLeaveReqDTO update, @MappingTarget LeaveRequest current);
}
