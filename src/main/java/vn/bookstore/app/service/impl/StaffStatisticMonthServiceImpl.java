package vn.bookstore.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.model.StaffStatisticMonth;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.ContractRepository;
import vn.bookstore.app.repository.SeniorityLevelRepository;
import vn.bookstore.app.repository.StaffStatisticMonthRepository;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.StaffStatisticMonthService;
import vn.bookstore.app.util.constant.GenderEnum;
import vn.bookstore.app.util.error.NotFoundValidException;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffStatisticMonthServiceImpl implements StaffStatisticMonthService {
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final StaffStatisticMonthRepository staffStatisticMonthRepository;
    private final SeniorityLevelRepository seniorityLevelRepository;



    public int calActiveEmployees(int month, int year) {
        int total = 0;
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        List<User> users = this.userRepository.findAllByStatusAndCreatedBefore(1,lastDayOfMonth);
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatus(user,1)) {
                total ++;
            }
        }
        return total;
    }

    public int calNewEmployees(int month, int year) {
        int total = 0;
        List<User> users = this.userRepository.findAllByStatusAndCreatedDate(1, year, month);
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatus(user, 1)) {
                total++;
            }
        }
        return total;
    }
        public int calContractExpired(int month, int year) {
            int total = 0;
            List<User> users = this.userRepository.findAllByStatus(1);
            for (User user : users) {
                if (contractRepository.existsByUserAndStatusAndExpiryYearAndMonth(user,1,year,month)) {
                    total ++;
                }
            }
        return total;
    }

    public float genderRatio(int month, int year) {
        int total_male = 0;
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        List<User> users = this.userRepository.findAllByGenderAndStatusAndCreatedBefore(1, GenderEnum.MALE,lastDayOfMonth);
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatus(user,1)) {
                total_male++;
            }
        }
        int totalActive = calActiveEmployees(month, year);
        if (totalActive == 0) return 0f;
        return (float) total_male / totalActive;
    }

    public int calculateAgeAtDate(LocalDate birthday, LocalDate referenceDate) {
        if (birthday == null || referenceDate == null) return 0;
        return Period.between(birthday, referenceDate).getYears();
    }


    public float avgAge(int month, int year) {
        int total_age = 0;
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        List<User> users = this.userRepository.findAllByStatusAndCreatedBefore(1,lastDayOfMonth);
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatus(user,1)) {
                total_age += calculateAgeAtDate(user.getDateOfBirth(), lastDayOfMonth); ;
            }
        }
        int totalActive = calActiveEmployees(month, year);
        if(totalActive == 0) return 0f;
        return (float) total_age / totalActive;
    }

    public float avgTenure(int month, int year) {
        int total_tenure = 0;
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        List<User> users = this.userRepository.findAllByStatusAndCreatedBefore(1,lastDayOfMonth);
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatus(user,1)) {
                total_tenure += calculateAgeAtDate(user.getCreatedAt().toLocalDate(), lastDayOfMonth); ;
            }
        }
        int totalActive = calActiveEmployees(month, year);
        if(totalActive == 0) return 0f;
        return (float) total_tenure / totalActive;
    }



    public int calProbationEmployees(int month, int year) {
        int total = 0;
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        List<User> users = this.userRepository.findAllByStatusAndCreatedBefore(1,lastDayOfMonth);
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatusAndSeniorityLevel(user, 1, this.seniorityLevelRepository.findSeniorityLevelByIdAndStatus(1,1) )) {
                total++;
            }
        }
        return total;
    }

        public void createStaffStatisticMonth(YearMonth yearMonth) {
        int month = yearMonth.getMonthValue();
        int year = yearMonth.getYear();
        StaffStatisticMonth statisticOfMonth = new StaffStatisticMonth();
        statisticOfMonth.setActiveEmployees(calActiveEmployees(month, year));
        statisticOfMonth.setMonthOfYear(yearMonth.toString());
        statisticOfMonth.setNewEmployees(calNewEmployees(month, year));
        statisticOfMonth.setContractExpired(calContractExpired(month, year));
        statisticOfMonth.setGenderRatio(genderRatio(month, year) * 100 + "%");
        statisticOfMonth.setAvgAge(avgAge(month, year));
        statisticOfMonth.setProbationEmployees(calProbationEmployees(month, year));
        statisticOfMonth.setPermanentEmployees(calActiveEmployees(month, year) - calProbationEmployees(month, year) );
        statisticOfMonth.setAvgTenure(avgTenure(month, year));
        this.staffStatisticMonthRepository.save(statisticOfMonth);


    }

    @Override
    public StaffStatisticMonth getStaffStatisticMonth(YearMonth yearMonth) {
        if (this.staffStatisticMonthRepository.findStaffStatisticOfMonthByMonthOfYear(yearMonth.toString()).isEmpty()) {
            createStaffStatisticMonth(yearMonth);
        }
        return this.staffStatisticMonthRepository.findStaffStatisticOfMonthByMonthOfYear(yearMonth.toString())
                .orElseThrow(() -> new NotFoundValidException("Thang thong ke khong ton tai"));
    }
}
