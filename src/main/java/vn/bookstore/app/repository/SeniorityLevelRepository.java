package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Role;
import vn.bookstore.app.model.SeniorityLevel;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeniorityLevelRepository extends JpaRepository<SeniorityLevel, Long>, JpaSpecificationExecutor<SeniorityLevel> {
    List<SeniorityLevel> findAllByStatusIn(List<Integer> status);
    Optional<SeniorityLevel> findSeniorityLevelByIdAndStatusIn(Long id, List<Integer> status);
    List<SeniorityLevel> findAllByStatusInAndRole(List<Integer> status, Role role);
    boolean existsByLevelNameAndStatusIn(String levelName, List<Integer> status);
    SeniorityLevel findSeniorityLevelByIdAndStatus(int id, int  status);

}

