package vn.bookstore.app.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import vn.bookstore.app.dto.response.ResAttendanceDetailDTO;
import vn.bookstore.app.model.AttendanceDetail;
import vn.bookstore.app.model.Holiday;

@Mapper(componentModel = "spring")
public interface AttendanceDetailMapper {
    AttendanceDetailMapper INSTANCE = Mappers.getMapper(AttendanceDetailMapper.class);

    @Mapping(source = "id", target = "idString", qualifiedByName = "formatId")
    @Mapping(source = "attendance.user.id", target = "userId")
    @Mapping(source = "attendance.id", target = "attendanceId")
    ResAttendanceDetailDTO convertToResAttendanceDetailDTO(AttendanceDetail attendanceDetail);
    @Named("formatId")
    static String formatId(Long id) {
        return "ATD-" + id;
    }


//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "id", ignore = true) // Không ghi đè ID
//    void updateHoliday(Holiday update, @MappingTarget Holiday current);
}
