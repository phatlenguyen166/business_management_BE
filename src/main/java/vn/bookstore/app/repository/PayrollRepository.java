package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Attendance;
import vn.bookstore.app.model.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    Payroll findPayrollByAttendance(Attendance attendance);
}
