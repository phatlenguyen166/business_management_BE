package vn.bookstore.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.model.Holiday;
import vn.bookstore.app.repository.HolidayRepository;
import vn.bookstore.app.service.PayrollService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {
    private final HolidayRepository holidayRepository;

    public boolean isHoliday(LocalDate date, List<Holiday> holidays) {
        for (Holiday holiday : holidays) {
            if (!date.isBefore(holiday.getStartDate()) && !date.isAfter(holiday.getEndDate())) {
                return true;
            }
        }
        return false;
    }

    public int getStandardWorkingDays(YearMonth yearMonth) {
        int workingDays = 0;
        List<Holiday> holidays = holidayRepository.findAllByStatus(1);
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = yearMonth.atDay(day);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY && !isHoliday(date,holidays)) {
                workingDays++;
            }
        }
        return workingDays;
    }
}
