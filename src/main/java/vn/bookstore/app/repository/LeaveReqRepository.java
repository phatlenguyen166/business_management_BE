package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.LeaveRequest;
import vn.bookstore.app.model.User;
import vn.bookstore.app.util.constant.LeaveTypeEnum;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;


@Repository
public interface LeaveReqRepository extends JpaRepository<LeaveRequest, Long>, JpaSpecificationExecutor<LeaveRequest> {
    List<LeaveRequest> findByStatusInOrderBySendDateDesc(List<Integer> statusList);
    Optional<LeaveRequest> findByIdAndStatusIn(Long id, List<Integer> statusList);
    Optional<LeaveRequest> findByIdAndStatus(Long id, int status);
    List<LeaveRequest> findByUserOrderBySendDateDesc(User user);
    List<LeaveRequest> findByUserAndStatus(User user, int status);

    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.user = :user AND lr.status = 1 AND lr.leaveReason = :leaveReason "+
            "AND MONTH(lr.startDate) = MONTH(:startDate) " +
            "AND YEAR(lr.startDate) = YEAR(:startDate)")
    LeaveRequest findByUserAndStatusAndMonth(@Param("user") User user,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("leaveReason")LeaveTypeEnum leaveReason);

    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.user = :user AND lr.status = 1 AND lr.leaveReason = :leaveReason "+
            "AND YEAR(lr.startDate) = YEAR(:startDate)")
    List<LeaveRequest> findByUserAndStatusAndYear(@Param("user") User user,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("leaveReason")LeaveTypeEnum leaveReason);


    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.user = :user AND  lr.status = :status AND :today" +
            " BETWEEN lr.startDate AND lr.endDate")
    Optional<LeaveRequest> findLeaveRequestByUserAndDateAndStatus(@Param("user") User user,
                                                                  @Param("today") LocalDate today,
                                                                  @Param("status")int status);

    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.user = :user AND lr.status = :status AND lr.leaveReason = :leaveReason " +
            "AND YEAR(lr.startDate) = :year AND YEAR(lr.endDate) = :year")
    List<LeaveRequest> findLeaveRequestsByUserAndYear(@Param("user") User user,
                                                      @Param("year") int year,
                                                      @Param("leaveReason") LeaveTypeEnum leaveReason,
                                                      @Param("status") int status);

    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.user = :user AND  lr.status = :status AND :today" +
            " BETWEEN lr.startDate AND lr.endDate")
    List<LeaveRequest> findAllLeaveRequestByUserAndDateAndStatus(@Param("user") User user,
                                                                  @Param("today") LocalDate today,
                                                                  @Param("status")int status);

}
