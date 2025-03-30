package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Allowance;

@Repository
public interface AllowanceRepository extends JpaRepository<Allowance, Long> {
}
