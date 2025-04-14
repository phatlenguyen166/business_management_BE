package vn.bookstore.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.bookstore.app.model.Attendance;
import vn.bookstore.app.model.AttendanceDetail;
import vn.bookstore.app.model.User;

@Repository
public interface AttendanceDetailRepository extends JpaRepository<AttendanceDetail, Long> {

    @Query("SELECT a FROM AttendanceDetail a WHERE a.attendance = :attendance AND  a.workingDay = :checkInDate")
    AttendanceDetail findByAttendanceAndCheckInDate(@Param("attendance") Attendance attendance,
            @Param("checkInDate") LocalDate checkInDate);

    List<AttendanceDetail> findAllByAttendance(Attendance attendance);

    List<AttendanceDetail> findAllByWorkingDay(LocalDate workingDay);

    @Query("SELECT a FROM AttendanceDetail a WHERE MONTH(a.workingDay) = :month AND YEAR(a.workingDay) = :year ")
    List<AttendanceDetail> findAllByMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT COUNT(a) > 0 FROM AttendanceDetail a WHERE a.workingDay = :workingDay AND a.attendance.user =:user ")
    boolean existsByWorkingDayAndUser(@Param("workingDay") LocalDate workingDay, @Param("user") User user);
}
