package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.bookstore.app.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // Custom queries can be added here if needed
}
