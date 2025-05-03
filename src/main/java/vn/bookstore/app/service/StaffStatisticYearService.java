package vn.bookstore.app.service;

import vn.bookstore.app.model.StaffStatisticYear;

import java.time.Year;

public interface StaffStatisticYearService {
    StaffStatisticYear getStaffStatisticYear(Year year);
}
