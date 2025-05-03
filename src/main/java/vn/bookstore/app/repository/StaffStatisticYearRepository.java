package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.StaffStatisticMonth;
import vn.bookstore.app.model.StaffStatisticYear;

import java.time.Year;
import java.util.Optional;

@Repository
public interface StaffStatisticYearRepository extends JpaRepository<StaffStatisticYear,Long> {
    Optional<StaffStatisticYear> findStaffStatisticYearByYear(String year);
}
