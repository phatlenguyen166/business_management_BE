package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.SeniorityLevel;
import vn.bookstore.app.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {
    List<Contract> getAllByStatus(int status);
    boolean existsContractByUserAndStatus(User user, int status);
    boolean existsContractByUserAndStatusAndSeniorityLevel(User user, int status, SeniorityLevel seniorityLevel);
    Optional<Contract> findContractByIdAndStatusIn(Long id, List<Integer> statusList);
    List<Contract> findAllByStatusInAndIdNotOrderByStartDateDesc(List<Integer> statusList, Long id);
    Optional<Contract> findContractByIdAndStatus(Long id, int status);
    Optional<Contract> findContractByUserAndStatus(User user, int status);

//    @Query(value = "select * from contracts where user_id = 2 order by start_date DESC", nativeQuery = true)
    @Query("select c from Contract c where c.user.id = :userId and c.status in (1,2) order by c.startDate desc")
    List<Contract> getAllByUserId(@Param("userId") Long userId);
    Contract findByEndDateBeforeAndStatus(LocalDate expiryDate, int status);

    @Query("SELECT c FROM Contract c WHERE c.status = :status " +
            "AND YEAR(c.startDate) = :year " +
            "AND MONTH(c.startDate) = :month")
    List<Contract> findAllByStatusAndStartDate(
            @Param("status") int status,
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("SELECT COUNT(c) > 0 FROM Contract c WHERE c.user = :user and c.status = :status " +
            "AND YEAR(c.expiryDate) = :year " +
            "AND MONTH(c.expiryDate) = :month")
    boolean existsByUserAndStatusAndExpiryYearAndMonth(
            @Param("user") User user,
            @Param("status") int status,
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("SELECT COUNT(c) > 0 FROM Contract c WHERE c.user = :user and c.status = :status " +
            "AND YEAR(c.expiryDate) = :year ")
    boolean existsByUserAndStatusAndExpiryYear(
            @Param("user") User user,
            @Param("status") int status,
            @Param("year") int year
    );

}
