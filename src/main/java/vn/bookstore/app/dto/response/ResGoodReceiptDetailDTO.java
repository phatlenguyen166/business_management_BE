package vn.bookstore.app.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ResGoodReceiptDetailDTO {
    private Long productId;
    private Integer quantity;
    private BigDecimal inputPrice;
}