package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Role;

import javax.management.relation.RoleStatus;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    Optional<Role> findByIdAndStatus(Long id, int status);
    
    List<Role> findAllByStatus(int status);
    
    boolean existsByNameAndStatus(String name, int status);
    
    Optional<Role> findRoleByIdAndStatus(Long id, int status);
    
    @Query("SELECT r FROM Role r " +
            "JOIN SeniorityLevel s ON r.id = s.role.id " +
            "JOIN Contract c ON s.id = c.seniorityLevel.id " +
            "JOIN User u ON c.user.id = u.id " +
            "WHERE u.id = :userId")
    Optional<Role> findRoleByUserId(Long userId);
}
