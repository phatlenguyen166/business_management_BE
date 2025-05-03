package vn.bookstore.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatisticYearDTO {
    private int year;
    private int totalProductsSold;
    private BigDecimal totalRevenue;
    private BigDecimal totalProfit;
    private BigDecimal totalCost;
    private Map<String, Integer> monthlySales; // Bán hàng theo tháng trong năm
    private Map<Integer, Integer> quarterlySales; // Bán hàng theo quý trong năm
    private List<ProductSalesDTO> topSellingProducts; // Top 10 sản phẩm bán chạy
    private List<CategorySalesDTO> categorySales; // Thống kê theo danh mục
}