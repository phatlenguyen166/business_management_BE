package vn.bookstore.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.response.ResHolidayDTO;
import vn.bookstore.app.mapper.HolidayMapper;
import vn.bookstore.app.model.Holiday;
import vn.bookstore.app.repository.HolidayRepository;
import vn.bookstore.app.service.HolidayService;
import vn.bookstore.app.util.error.InvalidRequestException;
import vn.bookstore.app.util.error.NotFoundValidException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {
    private final HolidayRepository holidayRepository;
    private final HolidayMapper holidayMapper;


    @Override
    public ResHolidayDTO handleCreateHoliday(Holiday holiday) {
        holiday.setStatus(1);
        Holiday newHoliday =  this.holidayRepository.save(holiday);
        return this.holidayMapper.convertToResHolidayDTO(newHoliday);
    }

    @Override
    public List<ResHolidayDTO> handleGetAllHoliday() {

        return this.holidayRepository.findAllByStatus(1).stream()
                .map(holidayMapper::convertToResHolidayDTO)
                .collect(Collectors.toList());
    }

    public Holiday getHolidayById(Long id) {
        return this.holidayRepository.findByIdAndStatus(id, 1).orElseThrow(() -> new NotFoundValidException("Ngãy lễ không tồn tại"));
    }

    @Override
    public ResHolidayDTO handleGetHolidayById(Long id) {
        Holiday holiday = getHolidayById(id);
        return this.holidayMapper.convertToResHolidayDTO(holiday);
    }

    @Override
    public ResHolidayDTO handleUpdateHoliday(Holiday holiday, Long id) {
        Holiday currHoliday = getHolidayById(id);
        this.holidayMapper.updateHoliday(holiday, currHoliday);
        this.holidayRepository.save(currHoliday);
        return this.holidayMapper.convertToResHolidayDTO(currHoliday);
    }

    @Override
    public void handleDeleteHoliday(Long id) {
        Holiday currHoliday = getHolidayById(id);
        LocalDate today = LocalDate.now();
        if (currHoliday.getStartDate().isAfter(today)) {
            currHoliday.setStatus(0);
            this.holidayRepository.save(currHoliday);
        } else {
            throw new  InvalidRequestException("Không thể xóa");
        }
    }
}
