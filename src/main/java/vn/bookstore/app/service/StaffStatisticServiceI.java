package vn.bookstore.app.service;

import vn.bookstore.app.model.StaffStatisticOfMonth;

import java.time.YearMonth;

public interface StaffStatisticServiceI {
    public StaffStatisticOfMonth getStaffStatisticMonth(YearMonth yearMonth);
}
