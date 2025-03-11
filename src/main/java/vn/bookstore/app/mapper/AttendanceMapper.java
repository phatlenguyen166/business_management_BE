package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.bookstore.app.dto.response.ResAttendanceDTO;
import vn.bookstore.app.dto.response.ResAttendanceDetailDTO;
import vn.bookstore.app.model.Attendance;
import vn.bookstore.app.model.AttendanceDetail;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {
    AttendanceMapper INSTANCE = Mappers.getMapper(AttendanceMapper.class);

    @Mapping(source = "id", target = "idString", qualifiedByName = "formatId")
    @Mapping(source = "user.id", target = "userId")
    ResAttendanceDTO convertToResAttendanceDTO(Attendance attendance);
    @Named("formatId")
    static String formatId(Long id) {
        return "AD-" + id;
    }
}
