package vn.bookstore.app.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResBillDetailDTO {

    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal subPrice;
}
