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
public class ProductImportStatisticMonthDTO {
    private String monthOfYear; // Format: "2025-04"
    private int totalProductsImported;
    private BigDecimal totalCost;
    private List<ProductImportDTO> topImportedProducts; // Top 5 sản phẩm nhập nhiều nhất
    private List<ProductImportDTO> allImportedProducts; // Tất cả sản phẩm đã nhập
    private List<SupplierImportDTO> supplierImports; // Thống kê theo nhà cung cấp
}