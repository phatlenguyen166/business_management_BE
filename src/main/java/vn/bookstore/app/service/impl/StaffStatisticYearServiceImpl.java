package vn.bookstore.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.model.StaffStatisticYear;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.*;
import vn.bookstore.app.service.StaffStatisticYearService;
import vn.bookstore.app.util.constant.GenderEnum;
import vn.bookstore.app.util.error.NotFoundValidException;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffStatisticYearServiceImpl implements StaffStatisticYearService {
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final StaffStatisticYearRepository staffStatisticYearRepository;
    private final SeniorityLevelRepository seniorityLevelRepository;

    public int calActiveEmployees(int year) {
        LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);
        List<User> users = userRepository.findAllByStatusAndCreatedBefore(1, lastDayOfYear);
        int total = 0;
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatus(user, 1)) {
                total++;
            }
        }
        return total;
    }

    public int calNewEmployees(int year) {
        List<User> users = userRepository.findAllByStatusAndCreatedYear(1, year);
        int total = 0;
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatus(user, 1)) {
                total++;
            }
        }
        return total;
    }

    public int calContractExpired(int year) {
        List<User> users = userRepository.findAllByStatus(1);
        int total = 0;
        for (User user : users) {
            if (contractRepository.existsByUserAndStatusAndExpiryYear(user, 1, year)) {
                total++;
            }
        }
        return total;
    }

    public float genderRatio(int year) {
        LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);
        List<User> users = userRepository.findAllByGenderAndStatusAndCreatedBefore(1, GenderEnum.MALE, lastDayOfYear);
        int totalMale = 0;
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatus(user, 1)) {
                totalMale++;
            }
        }
        int totalActive = calActiveEmployees(year);
        if (totalActive == 0) return 0f;
        return (float) totalMale / totalActive;
    }

    public int calculateAgeAtDate(LocalDate birthday, LocalDate referenceDate) {
        if (birthday == null || referenceDate == null) return 0;
        return Period.between(birthday, referenceDate).getYears();
    }

    public float avgAge(int year) {
        LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);
        List<User> users = userRepository.findAllByStatusAndCreatedBefore(1, lastDayOfYear);
        int totalAge = 0;
        int count = 0;
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatus(user, 1)) {
                totalAge += calculateAgeAtDate(user.getDateOfBirth(), lastDayOfYear);
                count++;
            }
        }
        if (count == 0) return 0f;
        return (float) totalAge / count;
    }

    public float avgTenure(int year) {
        LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);
        List<User> users = userRepository.findAllByStatusAndCreatedBefore(1, lastDayOfYear);
        int totalTenure = 0;
        int count = 0;
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatus(user, 1)) {
                totalTenure += calculateAgeAtDate(user.getCreatedAt().toLocalDate(), lastDayOfYear);
                count++;
            }
        }
        if (count == 0) return 0f;
        return (float) totalTenure / count;
    }

    public int calProbationEmployees(int year) {
        LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);
        List<User> users = userRepository.findAllByStatusAndCreatedBefore(1, lastDayOfYear);
        int total = 0;
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatusAndSeniorityLevel(user, 1, seniorityLevelRepository.findSeniorityLevelByIdAndStatus(1, 1))) {
                total++;
            }
        }
        return total;
    }

    public void createStaffStatisticYear(int year) {
        StaffStatisticYear stat = new StaffStatisticYear();
        stat.setYear(String.valueOf(year));
        stat.setActiveEmployees(calActiveEmployees(year));
        stat.setNewEmployees(calNewEmployees(year));
        stat.setContractExpired(calContractExpired(year));
        stat.setGenderRatio(genderRatio(year) * 100 + "%");
        stat.setAvgAge(avgAge(year));
        stat.setAvgTenure(avgTenure(year));
        stat.setProbationEmployees(calProbationEmployees(year));
        stat.setPermanentEmployees(stat.getActiveEmployees() - stat.getProbationEmployees());

        staffStatisticYearRepository.save(stat);
    }

    @Override
    public StaffStatisticYear getStaffStatisticYear(Year year) {
        return staffStatisticYearRepository.findStaffStatisticYearByYear(year.toString())
                .orElseGet(() -> {
                    createStaffStatisticYear(year.getValue());
                    return staffStatisticYearRepository.findStaffStatisticYearByYear(year.toString())
                            .orElseThrow(() -> new NotFoundValidException("Năm thống kê không tồn tại"));
                });
    }



}
