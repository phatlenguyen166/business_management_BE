package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {
    List<Contract> getAllByStatus(int status);
    boolean existsContractByUser(User user);
    Optional<Contract> findContractByIdAndStatusIn(Long id, List<Integer> statusList);
    List<Contract> findAllContractByStatusInOrderByStartDateDesc(List<Integer> statusList);
    Optional<Contract> findContractByIdAndStatus(Long id, int status);
    Optional<Contract> findContractByUserId(Long userId);

//    @Query(value = "select * from contracts where user_id = 2 order by start_date DESC", nativeQuery = true)
    @Query("select c from Contract c where c.user.id = :userId and c.status in (1,2) order by c.startDate desc")
    List<Contract> getAllByUserId(@Param("userId") Long userId);
    Contract findByExpiryDateBeforeAndStatus(LocalDate expiryDate, int status);
}
