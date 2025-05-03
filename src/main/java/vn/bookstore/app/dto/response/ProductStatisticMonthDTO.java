package vn.bookstore.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatisticMonthDTO {
    private String monthOfYear; // Format: "2025-04"
    private int totalProductsSold;
    private BigDecimal totalRevenue;
    private BigDecimal totalProfit;
    private BigDecimal totalCost;
    private List<ProductSalesDTO> topSellingProducts; // Top 5 sản phẩm bán chạy
    private List<CategorySalesDTO> categorySales; // Thống kê theo danh mục
}
