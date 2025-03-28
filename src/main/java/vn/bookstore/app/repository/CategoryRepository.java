package vn.bookstore.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.bookstore.app.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    Optional<Category> findByIdAndStatus(Long id, int status);

    List<Category> findAllByStatus(int status);

    boolean existsByNameAndIdNot(String name, Long id);
}
