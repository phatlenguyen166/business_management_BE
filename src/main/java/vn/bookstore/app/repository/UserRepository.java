package vn.bookstore.app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.User;
import vn.bookstore.app.util.constant.GenderEnum;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByIdAndStatus(Long id, int status);

    Optional<User> findByUsernameAndStatus(String username, int status);

    boolean existsByUsername(String username);

    List<User> findAllByStatus(int status);

    Optional<User> findUserByIdAndStatus(Long id, int status);

    Optional<User> findUserByIdAndStatusAndUsernameNot(Long id, int status, String username);

    boolean existsByIdAndStatus(Long id, int status);

    Optional<User> findUserByEmail(String email);

    User findByEmail(String email);

    User findByUsername(String userName);

    @Query("SELECT u FROM User u WHERE u.status = :status " +
            " AND u.username != 'ADMIN' " +
            "AND YEAR(u.createdAt) = :year " +
            "AND MONTH(u.createdAt) = :month")
    List<User> findAllByStatusAndCreatedDate(
            @Param("status") int status,
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("SELECT u FROM User u WHERE u.status = :status " +
            " AND u.username != 'ADMIN' " +
            "AND YEAR(u.createdAt) = :year " )
    List<User> findAllByStatusAndCreatedYear(
            @Param("status") int status,
            @Param("year") int year
    );

    @Query("SELECT u FROM User u WHERE u.status = :status " +
            "AND u.username != 'ADMIN' " +
            "AND DATE(u.createdAt) <= :beforeDate")
    List<User> findAllByStatusAndCreatedBefore(
            @Param("status") int status,
            @Param("beforeDate") LocalDate beforeDate
    );

    @Query("SELECT u FROM User u WHERE u.status = :status " +
            "AND u.username != 'ADMIN' " +
            "AND u.gender = :gender " +
            "AND DATE(u.createdAt) <= :beforeDate")
    List<User> findAllByGenderAndStatusAndCreatedBefore(
            @Param("status") int status,
            @Param("gender") GenderEnum genderEnum,
            @Param("beforeDate") LocalDate beforeDate
    );

}
