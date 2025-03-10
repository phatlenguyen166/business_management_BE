package vn.bookstore.app.dto.response;



import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class ResHolidayDTO {
    private Long id;
    private String idString;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Integer status;
}
