package vn.bookstore.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.bookstore.app.model.GoodReceipt;

public interface GoodReceiptRepository  extends JpaRepository<GoodReceipt, Long> {

    List<GoodReceipt> findByCreatedAtBetween(LocalDateTime atStartOfDay, LocalDateTime atStartOfDay2);
}
