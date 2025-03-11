package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Attendance;
import vn.bookstore.app.model.User;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Attendance findByUserAndMonthOfYear(User user, String monthOfYear);
    Optional<Attendance> findById(Long id);
    List<Attendance> findAllByOrderByMonthOfYearDesc();
    List<Attendance> findAllByUserOrderByMonthOfYearDesc(User user);

}
