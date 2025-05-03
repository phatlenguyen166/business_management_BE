package vn.bookstore.app.service;

import vn.bookstore.app.dto.response.ProductStatisticMonthDTO;
import vn.bookstore.app.dto.response.ProductStatisticQuarterDTO;
import vn.bookstore.app.dto.response.ProductStatisticYearDTO;

import java.time.Year;
import java.time.YearMonth;

public interface ProductStatisticService {
    /**
     * Lấy thống kê sản phẩm và lợi nhuận theo tháng
     */
    ProductStatisticMonthDTO getProductStatisticsByMonth(YearMonth yearMonth);

    /**
     * Lấy thống kê sản phẩm và lợi nhuận theo quý
     */
    ProductStatisticQuarterDTO getProductStatisticsByQuarter(int year, int quarter);

    /**
     * Lấy thống kê sản phẩm và lợi nhuận theo năm
     */
    ProductStatisticYearDTO getProductStatisticsByYear(Year year);

    /**
     * Tạo báo cáo PDF thống kê sản phẩm theo tháng
     */
    String generateProductStatisticMonthPdf(YearMonth yearMonth);

    /**
     * Tạo báo cáo PDF thống kê sản phẩm theo quý
     */
    String generateProductStatisticQuarterPdf(int year, int quarter);

    /**
     * Tạo báo cáo PDF thống kê sản phẩm theo năm
     */
    String generateProductStatisticYearPdf(Year year);
}
