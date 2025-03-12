package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
     Optional<User> findByIdAndStatus(Long id, int status);
     Optional<User> findByUsernameAndStatus(String username, int status);
     boolean existsByUsername(String username);
     List<User> findAllByStatus(int status);
     Optional<User> findUserByIdAndStatus(Long id, int status);
     boolean existsByIdAndStatus(Long id, int status);

}
