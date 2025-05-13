package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.StaffStatisticMonth;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffStatisticMonthRepository extends JpaRepository<StaffStatisticMonth,Long> {
    Optional<StaffStatisticMonth> findStaffStatisticOfMonthByMonthOfYear(String monthOfYear);

    @Query("SELECT p FROM StaffStatisticMonth p  WHERE SUBSTRING(p.monthOfYear, 1, 4) = :year  ORDER BY p.monthOfYear ASC ")
    List<StaffStatisticMonth> findAllByYear(@Param("year") String year);
}
