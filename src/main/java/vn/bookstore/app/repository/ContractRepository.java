package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {
    List<Contract> getAllByStatus(int status);
    boolean existsContractByUser(User user);
    Optional<Contract> findContractByIdAndStatus(Long id, int status);
    Optional<Contract> findContractByUserId(Long userId);

    @Query(value = "select * from contracts where user_id = 2 order by start_date DESC", nativeQuery = true)
    List<Contract> getAllByUserId(Long userId);
}
