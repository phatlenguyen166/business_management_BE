package vn.bookstore.app.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import vn.bookstore.app.dto.response.ResHolidayDTO;
import vn.bookstore.app.model.Holiday;


@Mapper(componentModel = "spring")
public interface HolidayMapper {
    HolidayMapper INSTANCE = Mappers.getMapper(HolidayMapper.class);

    @Mapping(source = "id", target = "idString", qualifiedByName = "formatId")
    ResHolidayDTO convertToResHolidayDTO(Holiday holiday);
    @Named("formatId")
    static String formatId(Long id) {
        return "HOL-" + id;
    }


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true) // Không ghi đè ID
    void updateHoliday(Holiday update, @MappingTarget Holiday current);
}
