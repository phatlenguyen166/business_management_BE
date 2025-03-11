package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.Holiday;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long>, JpaSpecificationExecutor<Holiday> {
    List<Holiday> findAllByStatus(Integer status);
    Optional<Holiday> findByIdAndStatus(Long id, Integer status);
    @Query("SELECT h FROM Holiday h WHERE h.status = 1 AND :today BETWEEN h.startDate AND h.endDate")
    Optional<Holiday> findHolidayByDate(@Param("today") LocalDate today);

}
