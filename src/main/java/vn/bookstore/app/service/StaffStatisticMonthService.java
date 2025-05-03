package vn.bookstore.app.service;

import vn.bookstore.app.model.StaffStatisticMonth;

import java.time.YearMonth;

public interface StaffStatisticMonthService {
    public StaffStatisticMonth getStaffStatisticMonth(YearMonth yearMonth);
}
