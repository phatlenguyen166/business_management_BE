package vn.bookstore.app.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResGoodReceiptDTO {
    private Long id;
    private Long userId;
    private Long supplierId;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ResGoodReceiptDetailDTO> goodReceiptDetails;
}
