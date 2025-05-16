package vn.bookstore.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.dto.response.*;
import vn.bookstore.app.service.ProductStatisticService;

import java.time.Year;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/v1/statistics/products")
@RequiredArgsConstructor
@Tag(name = "Product Statistics", description = "API thống kê sản phẩm")
public class ProductStatisticController {

    private final ProductStatisticService productStatisticService;

    @Operation(summary = "Thống kê sản phẩm theo tháng", description = "Thống kê số lượng sản phẩm đã xuất và lợi nhuận theo tháng")
    @GetMapping("/month/{year}/{month}")
    public ResponseEntity<ResponseDTO<ProductStatisticMonthDTO>> getProductStatisticsByMonth(
            @PathVariable("year") int year,
            @PathVariable("month") int month) {

        YearMonth yearMonth = YearMonth.of(year, month);
        ProductStatisticMonthDTO statistics = productStatisticService.getProductStatisticsByMonth(yearMonth);
        return ResponseEntity.ok(ResponseDTO.success(true, "Thống kê thành công!", statistics));

    }

    @Operation(summary = "Thống kê sản phẩm theo quý", description = "Thống kê số lượng sản phẩm đã xuất và lợi nhuận theo quý")
    @GetMapping("/quarter/{year}/{quarter}")
    public ResponseEntity<ResponseDTO<ProductStatisticQuarterDTO>> getProductStatisticsByQuarter(
            @PathVariable("year") int year,
            @PathVariable("quarter") int quarter) {

        if (quarter < 1 || quarter > 4) {
            throw new IllegalArgumentException("Quý phải có giá trị từ 1 đến 4");
        }

        ProductStatisticQuarterDTO statistics = productStatisticService.getProductStatisticsByQuarter(year, quarter);

        return ResponseEntity.ok(ResponseDTO.success(true, "Thống kê thành công!", statistics));
    }

    @Operation(summary = "Thống kê sản phẩm theo năm", description = "Thống kê số lượng sản phẩm đã xuất và lợi nhuận theo năm")
    @GetMapping("/year/{year}")
    public ResponseEntity<ResponseDTO<ProductStatisticYearDTO>> getProductStatisticsByYear(
            @PathVariable("year") int year) {

        Year yearValue = Year.of(year);
        ProductStatisticYearDTO statistics = productStatisticService.getProductStatisticsByYear(yearValue);

        return ResponseEntity.ok(ResponseDTO.success(true, "Thống kê thành công!", statistics));
    }

    @Operation(summary = "Xuất báo cáo PDF thống kê sản phẩm theo tháng", description = "Tạo file PDF báo cáo thống kê sản phẩm theo tháng")
    @GetMapping("/month/{year}/{month}/export")
    public ResponseEntity<ResponseDTO<String>> exportProductStatisticsByMonth(
            @PathVariable("year") int year,
            @PathVariable("month") int month) {

        YearMonth yearMonth = YearMonth.of(year, month);
        String filePath = productStatisticService.generateProductStatisticMonthPdf(yearMonth);

        return ResponseEntity.ok(ResponseDTO.success(true, "Thống kê thành công!", filePath));
    }

    @Operation(summary = "Xuất báo cáo PDF thống kê sản phẩm theo quý", description = "Tạo file PDF báo cáo thống kê sản phẩm theo quý")
    @GetMapping("/quarter/{year}/{quarter}/export")
    public ResponseEntity<ResponseDTO<String>> exportProductStatisticsByQuarter(
            @PathVariable("year") int year,
            @PathVariable("quarter") int quarter) {

        if (quarter < 1 || quarter > 4) {
            throw new IllegalArgumentException("Quý phải có giá trị từ 1 đến 4");
        }

        String filePath = productStatisticService.generateProductStatisticQuarterPdf(year, quarter);

        return ResponseEntity.ok(ResponseDTO.success(true, "Thống kê thành công!", filePath));
    }

    @Operation(summary = "Xuất báo cáo PDF thống kê sản phẩm theo năm", description = "Tạo file PDF báo cáo thống kê sản phẩm theo năm")
    @GetMapping("/year/{year}/export")
    public ResponseEntity<ResponseDTO<String>> exportProductStatisticsByYear(
            @PathVariable("year") int year) {

        Year yearValue = Year.of(year);
        String filePath = productStatisticService.generateProductStatisticYearPdf(yearValue);

        return ResponseEntity.ok(ResponseDTO.success(true, "Thống kê thành công!", filePath));
    }

    @Operation(summary = "Thống kê sản phẩm đã nhập theo tháng", description = "Thống kê số lượng sản phẩm đã nhập và chi phí theo tháng")
    @GetMapping("/imports/month/{year}/{month}")
    public ResponseEntity<ResponseDTO<ProductImportStatisticMonthDTO>> getProductImportStatisticsByMonth(
            @PathVariable("year") int year,
            @PathVariable("month") int month) {

        YearMonth yearMonth = YearMonth.of(year, month);
        ProductImportStatisticMonthDTO statistics = productStatisticService.getProductImportStatisticsByMonth(yearMonth);
        return ResponseEntity.ok(ResponseDTO.success(true, "Thống kê thành công!", statistics));
    }

    @Operation(summary = "Thống kê sản phẩm đã nhập theo năm", description = "Thống kê số lượng sản phẩm đã nhập và chi phí theo năm")
    @GetMapping("/imports/year/{year}")
    public ResponseEntity<ResponseDTO<ProductImportStatisticYearDTO>> getProductImportStatisticsByYear(
            @PathVariable("year") int year) {

        Year yearValue = Year.of(year);
        ProductImportStatisticYearDTO statistics = productStatisticService.getProductImportStatisticsByYear(yearValue);
        return ResponseEntity.ok(ResponseDTO.success(true, "Thống kê thành công!", statistics));
    }
}
