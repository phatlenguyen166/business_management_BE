package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.bookstore.app.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    boolean existsByName(String name);
    
    boolean existsByPhoneNumber(String phoneNumber);
}
