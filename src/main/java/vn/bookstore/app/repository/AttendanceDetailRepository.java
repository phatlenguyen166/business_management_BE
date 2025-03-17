package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Attendance;
import vn.bookstore.app.model.AttendanceDetail;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public interface AttendanceDetailRepository extends JpaRepository<AttendanceDetail, Long> {
    @Query("SELECT a FROM AttendanceDetail a WHERE a.attendance = :attendance AND  a.workingDay = :checkInDate")
    AttendanceDetail findByAttendanceAndCheckInDate(@Param("attendance") Attendance attendance,
                                                                @Param("checkInDate") LocalDate checkInDate);
    List<AttendanceDetail> findAllByAttendance(Attendance attendance);
    List<AttendanceDetail> findAllByWorkingDay(LocalDate workingDay);

    @Query("SELECT a FROM AttendanceDetail a WHERE MONTH(a.workingDay) = MONTH(:yearMonth) AND YEAR(a.workingDay) = YEAR(a.workingDay) ")
    List<AttendanceDetail> findAllByMonth(@Param("yearMonth") YearMonth yearMonth);

}
