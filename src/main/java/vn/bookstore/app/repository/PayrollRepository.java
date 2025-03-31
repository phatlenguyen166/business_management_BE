package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Attendance;
import vn.bookstore.app.model.Payroll;
import vn.bookstore.app.model.User;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    Payroll findPayrollByAttendance(Attendance attendance);
    @Query("SELECT p FROM Payroll p WHERE p.attendance.monthOfYear =:yearMonth")
    List<Payroll> findAllByYearMonth(@Param("yearMonth") String yearMonth);

    @Query("SELECT pr from Payroll pr where pr.attendance.user =:user order by pr.attendance.monthOfYear ")
    List<Payroll> findAllPayrollByUserOrderByMonthOfYear(@Param("user") User user);

    @Query("SELECT pr from Payroll pr where SUBSTRING(pr.attendance.monthOfYear,1,4) =:year AND pr.attendance.user =:user ")
    List<Payroll> findAllPayrollByUserByYear(@Param("year") String year , @Param("user") User user);

    @Query("SELECT pr from Payroll pr where SUBSTRING(pr.attendance.monthOfYear,1,4) =:year ")
    List<Payroll> findAllPayrollByYear(@Param("year") String year );

    Optional<Payroll> findPayrollById(Long id);
}
