package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    boolean existsByPhoneNumber(String phoneNumber);
    
    boolean existsByName(String name);
    
    boolean existsByEmail(String email);
}
