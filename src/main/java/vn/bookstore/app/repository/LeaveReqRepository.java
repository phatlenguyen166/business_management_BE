package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.LeaveRequest;
import vn.bookstore.app.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface LeaveReqRepository extends JpaRepository<LeaveRequest, Long>, JpaSpecificationExecutor<LeaveRequest> {
    List<LeaveRequest> findByStatusInOrderBySendDateDesc(List<Integer> statusList);
    Optional<LeaveRequest> findByIdAndStatusIn(Long id, List<Integer> statusList);
    List<LeaveRequest> findByUserOrderBySendDateDesc(User user);

    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.user = :user AND  lr.status = 1 AND :today BETWEEN lr.startDate AND lr.endDate")
    Optional<LeaveRequest> findLeaveRequestByUserAndDate(@Param("user") User user, @Param("today") LocalDate today);
}
