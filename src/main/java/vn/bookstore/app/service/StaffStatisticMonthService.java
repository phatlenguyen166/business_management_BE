package vn.bookstore.app.service;

import vn.bookstore.app.model.StaffStatisticMonth;

import java.time.Year;
import java.time.YearMonth;
import java.util.List;

public interface StaffStatisticMonthService {
    public StaffStatisticMonth getStaffStatisticMonth(YearMonth yearMonth);
    public List<StaffStatisticMonth> getAllStaffStatisticMonth(Year year);
}
