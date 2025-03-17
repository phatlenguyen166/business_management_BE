package vn.bookstore.app.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import vn.bookstore.app.dto.response.ResAttendanceDetailDTO;
import vn.bookstore.app.model.AttendanceDetail;
import vn.bookstore.app.model.Holiday;

import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface AttendanceDetailMapper {
    AttendanceDetailMapper INSTANCE = Mappers.getMapper(AttendanceDetailMapper.class);

    @Mapping(source = "id", target = "idString", qualifiedByName = "formatId")
    @Mapping(source = "attendance.user.id", target = "userId")
    @Mapping(source = "attendance.user.id", target = "userIdString", qualifiedByName = "formatIdUser")
    @Mapping(source = "attendance.user.fullName", target = "fullName")
    @Mapping(source = "attendance.id", target = "attendanceId")
    ResAttendanceDetailDTO convertToResAttendanceDetailDTO(AttendanceDetail attendanceDetail);
    @Named("formatId")
    static String formatId(Long id) {
        return "ATD-" + id;
    }
    @Named("formatIdUser")
    static String formatIdUser(Long id) {
        return "NV-" + id;
    }



//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "id", ignore = true) // Không ghi đè ID
//    void updateHoliday(Holiday update, @MappingTarget Holiday current);
}
