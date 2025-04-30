package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.StaffStatisticOfMonth;

import java.util.Optional;

@Repository
public interface StaffStatisticOfMonthRepository extends JpaRepository<StaffStatisticOfMonth,Long> {
    Optional<StaffStatisticOfMonth> findStaffStatisticOfMonthByMonthOfYear(String monthOfYear);
}
