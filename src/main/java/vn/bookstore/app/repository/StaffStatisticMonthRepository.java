package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.StaffStatisticMonth;

import java.util.Optional;

@Repository
public interface StaffStatisticMonthRepository extends JpaRepository<StaffStatisticMonth,Long> {
    Optional<StaffStatisticMonth> findStaffStatisticOfMonthByMonthOfYear(String monthOfYear);
}
