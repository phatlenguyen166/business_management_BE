package vn.bookstore.app.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
public class ResBillDTO {

    private Long id;
    private String idString; 
    private Long userId;
    private CustomerInfoDTO customerInfo;
    private BigDecimal totalPrice;
    private Integer totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ResBillDetailDTO> billDetails;
}
