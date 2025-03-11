package vn.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.bookstore.app.model.GoodReceipt;

public interface GoodReceiptRepository  extends JpaRepository<GoodReceipt, Long> {
}
