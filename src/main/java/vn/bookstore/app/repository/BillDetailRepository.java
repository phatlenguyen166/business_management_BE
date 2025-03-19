package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.bookstore.app.model.BillDetail;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail,Long> {
}
