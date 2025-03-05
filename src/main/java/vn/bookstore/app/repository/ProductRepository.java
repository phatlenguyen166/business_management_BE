package vn.bookstore.app.repository;

import lombok.NonNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.model.Product;
import vn.bookstore.app.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    boolean existsByName(String name);
}
