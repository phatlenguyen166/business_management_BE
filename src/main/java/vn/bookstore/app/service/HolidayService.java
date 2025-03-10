package vn.bookstore.app.service;

import vn.bookstore.app.dto.response.ResHolidayDTO;
import vn.bookstore.app.model.Holiday;

import java.util.List;

public interface HolidayService {
    public ResHolidayDTO handleCreateHoliday(Holiday holiday);
    public List<ResHolidayDTO> handleGetAllHoliday();
    public ResHolidayDTO handleGetHolidayById(Long id);
    public ResHolidayDTO handleUpdateHoliday(Holiday holiday, Long id);
    public void handleDeleteHoliday(Long id);
}
