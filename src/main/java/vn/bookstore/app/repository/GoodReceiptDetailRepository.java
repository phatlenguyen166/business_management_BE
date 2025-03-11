package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.bookstore.app.model.GoodReceiptDetail;

public interface GoodReceiptDetailRepository extends JpaRepository<GoodReceiptDetail, Long> {
}
