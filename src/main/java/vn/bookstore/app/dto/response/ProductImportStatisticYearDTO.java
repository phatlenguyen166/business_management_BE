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
public class ProductImportStatisticYearDTO {
    private int year;
    private int totalProductsImported;
    private BigDecimal totalCost;
    private Map<String, Integer> monthlyImports; // Nhập hàng theo tháng trong năm
    private Map<Integer, Integer> quarterlyImports; // Nhập hàng theo quý trong năm
    private List<ProductImportDTO> topImportedProducts; // Top 10 sản phẩm nhập nhiều nhất
    private List<ProductImportDTO> allImportedProducts; // Tất cả sản phẩm đã nhập
    private List<SupplierImportDTO> supplierImports; // Thống kê theo nhà cung cấp
}