package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.LeaveRequest;

import java.util.List;
import java.util.Optional;


@Repository
public interface LeaveReqRepository extends JpaRepository<LeaveRequest, Long>, JpaSpecificationExecutor<LeaveRequest> {
    List<LeaveRequest> findByStatusIn(List<Integer> statusList);
    Optional<LeaveRequest> findByIdAndStatusIn(Long id, List<Integer> statusList);
}
