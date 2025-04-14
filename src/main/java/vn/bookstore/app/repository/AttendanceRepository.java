package vn.bookstore.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import vn.bookstore.app.model.Attendance;
import vn.bookstore.app.model.User;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Attendance findByUserAndMonthOfYear(User user, String monthOfYear);

    @Override
    @NonNull
    Optional<Attendance> findById(@NonNull Long id);

    List<Attendance> findAllByOrderByMonthOfYearDesc();

    List<Attendance> findAllByUserOrderByMonthOfYearDesc(User user);

    List<Attendance> findAllByMonthOfYear(String monthOfYear);

}
