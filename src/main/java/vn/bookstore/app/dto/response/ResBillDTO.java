package vn.bookstore.app.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vn.bookstore.app.model.BillDetail;
import vn.bookstore.app.model.Customer;
import vn.bookstore.app.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResBillDTO {
    private Long id;
    private Long userId;
    private Long customerId;
    private BigDecimal totalPrice;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ResBillDetailDTO> billDetails;
}
