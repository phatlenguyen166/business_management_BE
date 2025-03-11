package vn.bookstore.app.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResAttendanceDTO {
    private Long id;
   private String idString;
    private String monthOfYear;
    private int totalWorkingDays;
    private int totalSickLeaves;
    private int totalPaidLeaves;
    private int totalMaternityLeaves;
    private int totalUnpaidLeaves;
    private int totalHolidayLeaves;
    private Long userId;

}
