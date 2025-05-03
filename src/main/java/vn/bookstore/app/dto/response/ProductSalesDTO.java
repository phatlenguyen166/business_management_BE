package vn.bookstore.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSalesDTO {
    private Long id;
    private String name;
    private int quantitySold;
    private BigDecimal revenue;
    private BigDecimal profit;
}