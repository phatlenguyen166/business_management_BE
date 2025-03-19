package vn.bookstore.app.dto.response;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResBillDetailDTO {
    private Long productId;
    private int quantity;
    private BigDecimal subPrice;
}
