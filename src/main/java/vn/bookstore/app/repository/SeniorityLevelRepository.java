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
    List<SeniorityLevel> findAllByStatus(int status);
    Optional<SeniorityLevel> findByIdAndStatus(Long id, int status);
    List<SeniorityLevel> findAllByStatusAndRole(int status, Role role);
    boolean existsByLevelNameAndStatus(String levelName, int status);
}
