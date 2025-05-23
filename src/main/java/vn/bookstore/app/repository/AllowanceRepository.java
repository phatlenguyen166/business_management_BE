package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Allowance;

import java.util.Optional;

@Repository
public interface AllowanceRepository extends JpaRepository<Allowance, Long> {
    Optional<Allowance> findAllowanceById (Long id);
}
