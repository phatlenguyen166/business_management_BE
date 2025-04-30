package vn.bookstore.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.model.StaffStatisticOfMonth;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.ContractRepository;
import vn.bookstore.app.repository.StaffStatisticOfMonthRepository;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.StaffStatisticServiceI;
import vn.bookstore.app.util.error.NotFoundValidException;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffStatisticServiceImpl implements StaffStatisticServiceI {
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final StaffStatisticOfMonthRepository staffStatisticOfMonthRepository;



    public int calActiveEmployees() {
        int total = 0;
        List<User> users = this.userRepository.findAllByStatus(1);
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatus(user,1)) {
                total ++;
            }
        }
        return total;
    }

    public int calNewEmployees(YearMonth yearMonth) {
        int month = yearMonth.getMonthValue();
        int year = yearMonth.getYear();
        int total = 0;
        List<User> users = this.userRepository.findAllByStatusAndCreatedDate(1, year, month);
        for (User user : users) {
            if (contractRepository.existsContractByUserAndStatus(user, 1)) {
                total++;
            }
        }
        return total;
    }
        public int calcontractExpired(YearMonth yearMonth) {
            int month = yearMonth.getMonthValue();
            int year = yearMonth.getYear();
            int total = 0;
            List<User> users = this.userRepository.findAllByStatusAndCreatedDate(1,year,month);
            for (User user : users) {
                if (contractRepository.existsContractByUserAndStatus(user,1)) {
                    total ++;
                }
            }
        return total;
    }
    public void createStaffStatisticMonth(YearMonth yearMonth) {
        StaffStatisticOfMonth statisticOfMonth = new StaffStatisticOfMonth();
        statisticOfMonth.setActiveEmployees(calActiveEmployees());
        statisticOfMonth.setMonthOfYear(yearMonth.toString());
        statisticOfMonth.setNewEmployees(calNewEmployees(yearMonth));
        this.staffStatisticOfMonthRepository.save(statisticOfMonth);


    }

    @Override
    public StaffStatisticOfMonth getStaffStatisticMonth(YearMonth yearMonth) {
        if (this.staffStatisticOfMonthRepository.findStaffStatisticOfMonthByMonthOfYear(yearMonth.toString()).isEmpty()) {
            createStaffStatisticMonth(yearMonth);
        }
        return this.staffStatisticOfMonthRepository.findStaffStatisticOfMonthByMonthOfYear(yearMonth.toString())
                .orElseThrow(() -> new NotFoundValidException("Thang thong ke khong ton tai"));
    }
}
