package vn.bookstore.app.service.impl;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import vn.bookstore.app.dto.response.CategorySalesDTO;
import vn.bookstore.app.dto.response.ProductSalesDTO;
import vn.bookstore.app.dto.response.ProductStatisticMonthDTO;
import vn.bookstore.app.dto.response.ProductStatisticQuarterDTO;
import vn.bookstore.app.dto.response.ProductStatisticYearDTO;
import vn.bookstore.app.model.Bill;
import vn.bookstore.app.model.BillDetail;
import vn.bookstore.app.model.Category;
import vn.bookstore.app.model.Product;
import vn.bookstore.app.repository.BillDetailRepository;
import vn.bookstore.app.repository.BillRepository;
import vn.bookstore.app.repository.CategoryRepository;
import vn.bookstore.app.repository.ProductRepository;
import vn.bookstore.app.service.ProductStatisticService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductStatisticServiceImpl implements ProductStatisticService {

    private final BillRepository billRepository;
    private final BillDetailRepository billDetailRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductStatisticMonthDTO getProductStatisticsByMonth(YearMonth yearMonth) {
        // Lấy các hóa đơn trong tháng
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Bill> bills = billRepository.findByCreatedAtBetween(startDate.atStartOfDay(),
                endDate.plusDays(1).atStartOfDay());

        if (bills.isEmpty()) {
            return ProductStatisticMonthDTO.builder()
                    .monthOfYear(yearMonth.toString())
                    .totalProductsSold(0)
                    .totalRevenue(BigDecimal.ZERO)
                    .totalCost(BigDecimal.ZERO)
                    .totalProfit(BigDecimal.ZERO)
                    .topSellingProducts(Collections.emptyList())
                    .categorySales(Collections.emptyList())
                    .build();
        }

        // Tính tổng doanh thu
        BigDecimal totalRevenue = bills.stream()
                .map(Bill::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Lấy chi tiết hóa đơn để tính các số liệu khác
        List<BillDetail> billDetails = new ArrayList<>();
        for (Bill bill : bills) {
            billDetails.addAll(bill.getBillDetails());
        }

        // Tính tổng số sản phẩm đã bán
        int totalProductsSold = billDetails.stream()
                .mapToInt(BillDetail::getQuantity)
                .sum();

        // Tính tổng chi phí (giá nhập x số lượng)
        BigDecimal totalCost = calculateTotalCost(billDetails);

        // Tính lợi nhuận
        BigDecimal totalProfit = totalRevenue.subtract(totalCost);

        // Lấy top 5 sản phẩm bán chạy nhất
        List<ProductSalesDTO> topSellingProducts = getTopSellingProducts(billDetails, 5);

        // Thống kê theo danh mục
        List<CategorySalesDTO> categorySales = getCategorySales(billDetails, totalRevenue);

        return ProductStatisticMonthDTO.builder()
                .monthOfYear(yearMonth.toString())
                .totalProductsSold(totalProductsSold)
                .totalRevenue(totalRevenue)
                .totalCost(totalCost)
                .totalProfit(totalProfit)
                .topSellingProducts(topSellingProducts)
                .categorySales(categorySales)
                .build();
    }

    @Override
    public ProductStatisticQuarterDTO getProductStatisticsByQuarter(int year, int quarter) {
        // Xác định các tháng trong quý
        int startMonth = (quarter - 1) * 3 + 1;

        LocalDate startDate = LocalDate.of(year, startMonth, 1);
        LocalDate endDate = LocalDate.of(year, startMonth + 2, Month.of(startMonth + 2).length(Year.isLeap(year)));

        List<Bill> bills = billRepository.findByCreatedAtBetween(startDate.atStartOfDay(),
                endDate.plusDays(1).atStartOfDay());

        if (bills.isEmpty()) {
            return ProductStatisticQuarterDTO.builder()
                    .year(year)
                    .quarter(quarter)
                    .totalProductsSold(0)
                    .totalRevenue(BigDecimal.ZERO)
                    .totalCost(BigDecimal.ZERO)
                    .totalProfit(BigDecimal.ZERO)
                    .monthlySales(new HashMap<>())
                    .topSellingProducts(Collections.emptyList())
                    .categorySales(Collections.emptyList())
                    .build();
        }

        // Tính tổng doanh thu
        BigDecimal totalRevenue = bills.stream()
                .map(Bill::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Lấy chi tiết hóa đơn để tính các số liệu khác
        List<BillDetail> billDetails = new ArrayList<>();
        for (Bill bill : bills) {
            billDetails.addAll(bill.getBillDetails());
        }

        // Tính tổng số sản phẩm đã bán
        int totalProductsSold = billDetails.stream()
                .mapToInt(BillDetail::getQuantity)
                .sum();

        // Tính tổng chi phí (giá nhập x số lượng)
        BigDecimal totalCost = calculateTotalCost(billDetails);

        // Tính lợi nhuận
        BigDecimal totalProfit = totalRevenue.subtract(totalCost);

        // Lấy top 5 sản phẩm bán chạy nhất
        List<ProductSalesDTO> topSellingProducts = getTopSellingProducts(billDetails, 5);

        // Thống kê theo danh mục
        List<CategorySalesDTO> categorySales = getCategorySales(billDetails, totalRevenue);

        // Thống kê doanh số theo tháng trong quý
        Map<String, Integer> monthlySales = getMonthlySales(bills, year, startMonth, startMonth + 2);

        return ProductStatisticQuarterDTO.builder()
                .year(year)
                .quarter(quarter)
                .totalProductsSold(totalProductsSold)
                .totalRevenue(totalRevenue)
                .totalCost(totalCost)
                .totalProfit(totalProfit)
                .topSellingProducts(topSellingProducts)
                .categorySales(categorySales)
                .monthlySales(monthlySales)
                .build();
    }

    @Override
    public ProductStatisticYearDTO getProductStatisticsByYear(Year year) {
        LocalDate startDate = year.atDay(1);
        LocalDate endDate = year.atMonth(12).atEndOfMonth();

        List<Bill> bills = billRepository.findByCreatedAtBetween(startDate.atStartOfDay(),
                endDate.plusDays(1).atStartOfDay());

        if (bills.isEmpty()) {
            return ProductStatisticYearDTO.builder()
                    .year(year.getValue())
                    .totalProductsSold(0)
                    .totalRevenue(BigDecimal.ZERO)
                    .totalCost(BigDecimal.ZERO)
                    .totalProfit(BigDecimal.ZERO)
                    .monthlySales(new HashMap<>())
                    .quarterlySales(new HashMap<>())
                    .topSellingProducts(Collections.emptyList())
                    .categorySales(Collections.emptyList())
                    .build();
        }

        // Tính tổng doanh thu
        BigDecimal totalRevenue = bills.stream()
                .map(Bill::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Lấy chi tiết hóa đơn để tính các số liệu khác
        List<BillDetail> billDetails = new ArrayList<>();
        for (Bill bill : bills) {
            billDetails.addAll(bill.getBillDetails());
        }

        // Tính tổng số sản phẩm đã bán
        int totalProductsSold = billDetails.stream()
                .mapToInt(BillDetail::getQuantity)
                .sum();

        // Tính tổng chi phí (giá nhập x số lượng)
        BigDecimal totalCost = calculateTotalCost(billDetails);

        // Tính lợi nhuận
        BigDecimal totalProfit = totalRevenue.subtract(totalCost);

        // Lấy top 10 sản phẩm bán chạy nhất
        List<ProductSalesDTO> topSellingProducts = getTopSellingProducts(billDetails, 10);

        // Thống kê theo danh mục
        List<CategorySalesDTO> categorySales = getCategorySales(billDetails, totalRevenue);

        // Thống kê doanh số theo tháng trong năm
        Map<String, Integer> monthlySales = getMonthlySales(bills, year.getValue(), 1, 12);

        // Thống kê doanh số theo quý trong năm
        Map<Integer, Integer> quarterlySales = getQuarterlySales(bills);

        return ProductStatisticYearDTO.builder()
                .year(year.getValue())
                .totalProductsSold(totalProductsSold)
                .totalRevenue(totalRevenue)
                .totalCost(totalCost)
                .totalProfit(totalProfit)
                .monthlySales(monthlySales)
                .quarterlySales(quarterlySales)
                .topSellingProducts(topSellingProducts)
                .categorySales(categorySales)
                .build();
    }

    @Override
    public String generateProductStatisticMonthPdf(YearMonth yearMonth) {
        ProductStatisticMonthDTO statistics = getProductStatisticsByMonth(yearMonth);

        String folderPath = "reports/statistics/products";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = "product_statistics_" + yearMonth.toString() + ".pdf";
        String filePath = folderPath + "/" + fileName;

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Thiết lập font chữ Unicode để hỗ trợ tiếng Việt
            BaseFont baseFont = BaseFont.createFont("fonts/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 18, Font.BOLD);
            Font headingFont = new Font(baseFont, 14, Font.BOLD);
            Font normalFont = new Font(baseFont, 12);
            Font boldFont = new Font(baseFont, 12, Font.BOLD);

            // Tiêu đề báo cáo
            Paragraph title = new Paragraph(
                    "BÁO CÁO THỐNG KÊ SẢN PHẨM THÁNG " + yearMonth.getMonthValue() + "/" + yearMonth.getYear(),
                    titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Thông tin tổng quan
            document.add(new Paragraph("THÔNG TIN TỔNG QUAN", headingFont));
            document.add(Chunk.NEWLINE);

            PdfPTable summaryTable = new PdfPTable(2);
            summaryTable.setWidthPercentage(100);

            addTableRow(summaryTable, "Tổng số sản phẩm đã bán:", String.valueOf(statistics.getTotalProductsSold()),
                    normalFont, boldFont);
            addTableRow(summaryTable, "Tổng doanh thu:", formatCurrency(statistics.getTotalRevenue()), normalFont,
                    boldFont);
            addTableRow(summaryTable, "Tổng chi phí:", formatCurrency(statistics.getTotalCost()), normalFont, boldFont);
            addTableRow(summaryTable, "Tổng lợi nhuận:", formatCurrency(statistics.getTotalProfit()), normalFont,
                    boldFont);

            document.add(summaryTable);
            document.add(Chunk.NEWLINE);

            // Top sản phẩm bán chạy
            document.add(new Paragraph("TOP SẢN PHẨM BÁN CHẠY", headingFont));
            document.add(Chunk.NEWLINE);

            if (statistics.getTopSellingProducts().isEmpty()) {
                document.add(new Paragraph("Không có dữ liệu", normalFont));
            } else {
                PdfPTable productsTable = new PdfPTable(5);
                productsTable.setWidthPercentage(100);

                // Header
                PdfPCell headerCell1 = new PdfPCell(new Phrase("STT", boldFont));
                PdfPCell headerCell2 = new PdfPCell(new Phrase("Tên sản phẩm", boldFont));
                PdfPCell headerCell3 = new PdfPCell(new Phrase("Số lượng", boldFont));
                PdfPCell headerCell4 = new PdfPCell(new Phrase("Doanh thu", boldFont));
                PdfPCell headerCell5 = new PdfPCell(new Phrase("Lợi nhuận", boldFont));

                productsTable.addCell(headerCell1);
                productsTable.addCell(headerCell2);
                productsTable.addCell(headerCell3);
                productsTable.addCell(headerCell4);
                productsTable.addCell(headerCell5);

                // Data
                int index = 1;
                for (ProductSalesDTO product : statistics.getTopSellingProducts()) {
                    productsTable.addCell(new PdfPCell(new Phrase(String.valueOf(index++), normalFont)));
                    productsTable.addCell(new PdfPCell(new Phrase(product.getName(), normalFont)));
                    productsTable
                            .addCell(new PdfPCell(new Phrase(String.valueOf(product.getQuantitySold()), normalFont)));
                    productsTable.addCell(new PdfPCell(new Phrase(formatCurrency(product.getRevenue()), normalFont)));
                    productsTable.addCell(new PdfPCell(new Phrase(formatCurrency(product.getProfit()), normalFont)));
                }

                document.add(productsTable);
            }

            document.add(Chunk.NEWLINE);

            // Thống kê theo danh mục
            document.add(new Paragraph("THỐNG KÊ THEO DANH MỤC", headingFont));
            document.add(Chunk.NEWLINE);

            if (statistics.getCategorySales().isEmpty()) {
                document.add(new Paragraph("Không có dữ liệu", normalFont));
            } else {
                PdfPTable categoriesTable = new PdfPTable(6);
                categoriesTable.setWidthPercentage(100);

                // Header
                PdfPCell headerCell1 = new PdfPCell(new Phrase("STT", boldFont));
                PdfPCell headerCell2 = new PdfPCell(new Phrase("Danh mục", boldFont));
                PdfPCell headerCell3 = new PdfPCell(new Phrase("Số lượng", boldFont));
                PdfPCell headerCell4 = new PdfPCell(new Phrase("Doanh thu", boldFont));
                PdfPCell headerCell5 = new PdfPCell(new Phrase("Lợi nhuận", boldFont));
                PdfPCell headerCell6 = new PdfPCell(new Phrase("Tỷ lệ (%)", boldFont));

                categoriesTable.addCell(headerCell1);
                categoriesTable.addCell(headerCell2);
                categoriesTable.addCell(headerCell3);
                categoriesTable.addCell(headerCell4);
                categoriesTable.addCell(headerCell5);
                categoriesTable.addCell(headerCell6);

                // Data
                int index = 1;
                for (CategorySalesDTO category : statistics.getCategorySales()) {
                    categoriesTable.addCell(new PdfPCell(new Phrase(String.valueOf(index++), normalFont)));
                    categoriesTable.addCell(new PdfPCell(new Phrase(category.getName(), normalFont)));
                    categoriesTable
                            .addCell(new PdfPCell(new Phrase(String.valueOf(category.getQuantitySold()), normalFont)));
                    categoriesTable
                            .addCell(new PdfPCell(new Phrase(formatCurrency(category.getRevenue()), normalFont)));
                    categoriesTable.addCell(new PdfPCell(new Phrase(formatCurrency(category.getProfit()), normalFont)));
                    categoriesTable.addCell(new PdfPCell(
                            new Phrase(String.format("%.2f", category.getPercentageOfTotal()), normalFont)));
                }

                document.add(categoriesTable);
            }

            // Thêm ngày tạo báo cáo
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            Paragraph dateCreated = new Paragraph("Báo cáo được tạo ngày: " +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont);
            dateCreated.setAlignment(Element.ALIGN_RIGHT);
            document.add(dateCreated);
            document.close();
            return filePath;
        } catch (DocumentException | IOException e) {
            log.error("Lỗi khi tạo báo cáo PDF", e);
            throw new RuntimeException("Không thể tạo báo cáo PDF: " + e.getMessage());
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }

    @Override
    public String generateProductStatisticQuarterPdf(int year, int quarter) {
        ProductStatisticQuarterDTO statistics = getProductStatisticsByQuarter(year, quarter);

        String folderPath = "reports/statistics/products";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = "product_statistics_" + year + "_Q" + quarter + ".pdf";
        String filePath = folderPath + "/" + fileName;
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Thiết lập font chữ Unicode để hỗ trợ tiếng Việt
            BaseFont baseFont = BaseFont.createFont("fonts/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 18, Font.BOLD);
            Font headingFont = new Font(baseFont, 14, Font.BOLD);
            Font normalFont = new Font(baseFont, 12);
            Font boldFont = new Font(baseFont, 12, Font.BOLD);

            // Tiêu đề báo cáo
            Paragraph title = new Paragraph("BÁO CÁO THỐNG KÊ SẢN PHẨM QUÝ " + quarter + " NĂM " + year, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Thông tin tổng quan
            document.add(new Paragraph("THÔNG TIN TỔNG QUAN", headingFont));
            document.add(Chunk.NEWLINE);

            PdfPTable summaryTable = new PdfPTable(2);
            summaryTable.setWidthPercentage(100);

            addTableRow(summaryTable, "Tổng số sản phẩm đã bán:", String.valueOf(statistics.getTotalProductsSold()),
                    normalFont, boldFont);
            addTableRow(summaryTable, "Tổng doanh thu:", formatCurrency(statistics.getTotalRevenue()), normalFont,
                    boldFont);
            addTableRow(summaryTable, "Tổng chi phí:", formatCurrency(statistics.getTotalCost()), normalFont, boldFont);
            addTableRow(summaryTable, "Tổng lợi nhuận:", formatCurrency(statistics.getTotalProfit()), normalFont,
                    boldFont);

            document.add(summaryTable);
            document.add(Chunk.NEWLINE);

            // Thống kê theo tháng trong quý
            document.add(new Paragraph("THỐNG KÊ THEO THÁNG", headingFont));
            document.add(Chunk.NEWLINE);

            if (statistics.getMonthlySales().isEmpty()) {
                document.add(new Paragraph("Không có dữ liệu", normalFont));
            } else {
                PdfPTable monthlyTable = new PdfPTable(4);
                monthlyTable.setWidthPercentage(100);

                // Header
                PdfPCell headerCell = new PdfPCell(new Phrase("Chỉ tiêu", boldFont));
                monthlyTable.addCell(headerCell);

                int startMonth = (quarter - 1) * 3 + 1;
                for (int i = 0; i < 3; i++) {
                    headerCell = new PdfPCell(new Phrase("Tháng " + (startMonth + i), boldFont));
                    monthlyTable.addCell(headerCell);
                }

                // Data row
                PdfPCell dataCell = new PdfPCell(new Phrase("Số lượng bán", normalFont));
                monthlyTable.addCell(dataCell);

                for (int i = 0; i < 3; i++) {
                    int month = startMonth + i;
                    String monthKey = year + "-" + (month < 10 ? "0" + month : month);
                    Integer sales = statistics.getMonthlySales().getOrDefault(monthKey, 0);
                    dataCell = new PdfPCell(new Phrase(String.valueOf(sales), normalFont));
                    monthlyTable.addCell(dataCell);
                }

                document.add(monthlyTable);
            }

            document.add(Chunk.NEWLINE);

            // Top sản phẩm bán chạy
            document.add(new Paragraph("TOP SẢN PHẨM BÁN CHẠY", headingFont));
            document.add(Chunk.NEWLINE);

            if (statistics.getTopSellingProducts().isEmpty()) {
                document.add(new Paragraph("Không có dữ liệu", normalFont));
            } else {
                PdfPTable productsTable = new PdfPTable(5);
                productsTable.setWidthPercentage(100);

                // Header
                PdfPCell headerCell1 = new PdfPCell(new Phrase("STT", boldFont));
                PdfPCell headerCell2 = new PdfPCell(new Phrase("Tên sản phẩm", boldFont));
                PdfPCell headerCell3 = new PdfPCell(new Phrase("Số lượng", boldFont));
                PdfPCell headerCell4 = new PdfPCell(new Phrase("Doanh thu", boldFont));
                PdfPCell headerCell5 = new PdfPCell(new Phrase("Lợi nhuận", boldFont));

                productsTable.addCell(headerCell1);
                productsTable.addCell(headerCell2);
                productsTable.addCell(headerCell3);
                productsTable.addCell(headerCell4);
                productsTable.addCell(headerCell5);

                // Data
                int index = 1;
                for (ProductSalesDTO product : statistics.getTopSellingProducts()) {
                    productsTable.addCell(new PdfPCell(new Phrase(String.valueOf(index++), normalFont)));
                    productsTable.addCell(new PdfPCell(new Phrase(product.getName(), normalFont)));
                    productsTable
                            .addCell(new PdfPCell(new Phrase(String.valueOf(product.getQuantitySold()), normalFont)));
                    productsTable.addCell(new PdfPCell(new Phrase(formatCurrency(product.getRevenue()), normalFont)));
                    productsTable.addCell(new PdfPCell(new Phrase(formatCurrency(product.getProfit()), normalFont)));
                }

                document.add(productsTable);
            }

            document.add(Chunk.NEWLINE);

            // Thống kê theo danh mục
            document.add(new Paragraph("THỐNG KÊ THEO DANH MỤC", headingFont));
            document.add(Chunk.NEWLINE);

            if (statistics.getCategorySales().isEmpty()) {
                document.add(new Paragraph("Không có dữ liệu", normalFont));
            } else {
                PdfPTable categoriesTable = new PdfPTable(6);
                categoriesTable.setWidthPercentage(100);

                // Header
                PdfPCell headerCell1 = new PdfPCell(new Phrase("STT", boldFont));
                PdfPCell headerCell2 = new PdfPCell(new Phrase("Danh mục", boldFont));
                PdfPCell headerCell3 = new PdfPCell(new Phrase("Số lượng", boldFont));
                PdfPCell headerCell4 = new PdfPCell(new Phrase("Doanh thu", boldFont));
                PdfPCell headerCell5 = new PdfPCell(new Phrase("Lợi nhuận", boldFont));
                PdfPCell headerCell6 = new PdfPCell(new Phrase("Tỷ lệ (%)", boldFont));

                categoriesTable.addCell(headerCell1);
                categoriesTable.addCell(headerCell2);
                categoriesTable.addCell(headerCell3);
                categoriesTable.addCell(headerCell4);
                categoriesTable.addCell(headerCell5);
                categoriesTable.addCell(headerCell6);

                // Data
                int index = 1;
                for (CategorySalesDTO category : statistics.getCategorySales()) {
                    categoriesTable.addCell(new PdfPCell(new Phrase(String.valueOf(index++), normalFont)));
                    categoriesTable.addCell(new PdfPCell(new Phrase(category.getName(), normalFont)));
                    categoriesTable
                            .addCell(new PdfPCell(new Phrase(String.valueOf(category.getQuantitySold()), normalFont)));
                    categoriesTable
                            .addCell(new PdfPCell(new Phrase(formatCurrency(category.getRevenue()), normalFont)));
                    categoriesTable.addCell(new PdfPCell(new Phrase(formatCurrency(category.getProfit()), normalFont)));
                    categoriesTable.addCell(new PdfPCell(
                            new Phrase(String.format("%.2f", category.getPercentageOfTotal()), normalFont)));
                }

                document.add(categoriesTable);
            }

            // Thêm ngày tạo báo cáo
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            Paragraph dateCreated = new Paragraph("Báo cáo được tạo ngày: " +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont);
            dateCreated.setAlignment(Element.ALIGN_RIGHT);
            document.add(dateCreated);

            return filePath;
        } catch (DocumentException | IOException e) {
            log.error("Lỗi khi tạo báo cáo PDF", e);
            throw new RuntimeException("Không thể tạo báo cáo PDF: " + e.getMessage());
        }
    }

    @Override
    public String generateProductStatisticYearPdf(Year year) {
        ProductStatisticYearDTO statistics = getProductStatisticsByYear(year);

        String folderPath = "reports/statistics/products";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            String fileName = "product_statistics_" + year.getValue() + ".pdf";
            String filePath = folderPath + "/" + fileName;
            Document document = new Document(PageSize.A4.rotate());
            try {
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                // Thiết lập font chữ Unicode để hỗ trợ tiếng Việt
                BaseFont baseFont = BaseFont.createFont("fonts/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font titleFont = new Font(baseFont, 18, Font.BOLD);
                Font headingFont = new Font(baseFont, 14, Font.BOLD);
                Font normalFont = new Font(baseFont, 12);
                Font boldFont = new Font(baseFont, 12, Font.BOLD);

                // Tiêu đề báo cáo
                Paragraph title = new Paragraph("BÁO CÁO THỐNG KÊ SẢN PHẨM NĂM " + year.getValue(), titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(Chunk.NEWLINE);

                // Thông tin tổng quan
                document.add(new Paragraph("THÔNG TIN TỔNG QUAN", headingFont));
                document.add(Chunk.NEWLINE);

                PdfPTable summaryTable = new PdfPTable(2);
                summaryTable.setWidthPercentage(100);

                addTableRow(summaryTable, "Tổng số sản phẩm đã bán:", String.valueOf(statistics.getTotalProductsSold()),
                        normalFont, boldFont);
                addTableRow(summaryTable, "Tổng doanh thu:", formatCurrency(statistics.getTotalRevenue()), normalFont,
                        boldFont);
                addTableRow(summaryTable, "Tổng chi phí:", formatCurrency(statistics.getTotalCost()), normalFont,
                        boldFont);
                addTableRow(summaryTable, "Tổng lợi nhuận:", formatCurrency(statistics.getTotalProfit()), normalFont,
                        boldFont);

                document.add(summaryTable);
                document.add(Chunk.NEWLINE);

                // Thống kê theo tháng
                document.add(new Paragraph("THỐNG KÊ THEO THÁNG", headingFont));
                document.add(Chunk.NEWLINE);

                if (statistics.getMonthlySales().isEmpty()) {
                    document.add(new Paragraph("Không có dữ liệu", normalFont));
                } else {
                    PdfPTable monthlyTable = new PdfPTable(13);
                    monthlyTable.setWidthPercentage(100);

                    // Header
                    PdfPCell headerCell = new PdfPCell(new Phrase("Chỉ tiêu", boldFont));
                    monthlyTable.addCell(headerCell);

                    for (int i = 1; i <= 12; i++) {
                        headerCell = new PdfPCell(new Phrase("Tháng " + i, boldFont));
                        monthlyTable.addCell(headerCell);
                    }

                    // Data row
                    PdfPCell dataCell = new PdfPCell(new Phrase("Số lượng bán", normalFont));
                    monthlyTable.addCell(dataCell);

                    for (int i = 1; i <= 12; i++) {
                        String monthKey = year.getValue() + "-" + (i < 10 ? "0" + i : i);
                        Integer sales = statistics.getMonthlySales().getOrDefault(monthKey, 0);
                        dataCell = new PdfPCell(new Phrase(String.valueOf(sales), normalFont));
                        monthlyTable.addCell(dataCell);
                    }

                    document.add(monthlyTable);
                }

                document.add(Chunk.NEWLINE);

                // Thống kê theo quý
                document.add(new Paragraph("THỐNG KÊ THEO QUÝ", headingFont));
                document.add(Chunk.NEWLINE);

                if (statistics.getQuarterlySales().isEmpty()) {
                    document.add(new Paragraph("Không có dữ liệu", normalFont));
                } else {
                    PdfPTable quarterlyTable = new PdfPTable(5);
                    quarterlyTable.setWidthPercentage(100);

                    // Header
                    PdfPCell headerCell = new PdfPCell(new Phrase("Chỉ tiêu", boldFont));
                    quarterlyTable.addCell(headerCell);

                    for (int i = 1; i <= 4; i++) {
                        headerCell = new PdfPCell(new Phrase("Quý " + i, boldFont));
                        quarterlyTable.addCell(headerCell);
                    }

                    // Data row
                    PdfPCell dataCell = new PdfPCell(new Phrase("Số lượng bán", normalFont));
                    quarterlyTable.addCell(dataCell);

                    for (int i = 1; i <= 4; i++) {
                        Integer sales = statistics.getQuarterlySales().getOrDefault(i, 0);
                        dataCell = new PdfPCell(new Phrase(String.valueOf(sales), normalFont));
                        quarterlyTable.addCell(dataCell);
                    }

                    document.add(quarterlyTable);
                }

                document.add(Chunk.NEWLINE);

                // Top sản phẩm bán chạy
                document.add(new Paragraph("TOP 10 SẢN PHẨM BÁN CHẠY", headingFont));
                document.add(Chunk.NEWLINE);

                if (statistics.getTopSellingProducts().isEmpty()) {
                    document.add(new Paragraph("Không có dữ liệu", normalFont));
                } else {
                    PdfPTable productsTable = new PdfPTable(5);
                    productsTable.setWidthPercentage(100);

                    // Header
                    PdfPCell headerCell1 = new PdfPCell(new Phrase("STT", boldFont));
                    PdfPCell headerCell2 = new PdfPCell(new Phrase("Tên sản phẩm", boldFont));
                    PdfPCell headerCell3 = new PdfPCell(new Phrase("Số lượng", boldFont));
                    PdfPCell headerCell4 = new PdfPCell(new Phrase("Doanh thu", boldFont));
                    PdfPCell headerCell5 = new PdfPCell(new Phrase("Lợi nhuận", boldFont));

                    productsTable.addCell(headerCell1);
                    productsTable.addCell(headerCell2);
                    productsTable.addCell(headerCell3);
                    productsTable.addCell(headerCell4);
                    productsTable.addCell(headerCell5);

                    // Data
                    int index = 1;
                    for (ProductSalesDTO product : statistics.getTopSellingProducts()) {
                        productsTable.addCell(new PdfPCell(new Phrase(String.valueOf(index++), normalFont)));
                        productsTable.addCell(new PdfPCell(new Phrase(product.getName(), normalFont)));
                        productsTable
                                .addCell(new PdfPCell(
                                        new Phrase(String.valueOf(product.getQuantitySold()), normalFont)));
                        productsTable
                                .addCell(new PdfPCell(new Phrase(formatCurrency(product.getRevenue()), normalFont)));
                        productsTable
                                .addCell(new PdfPCell(new Phrase(formatCurrency(product.getProfit()), normalFont)));
                    }

                    document.add(productsTable);
                }

                document.add(Chunk.NEWLINE);

                // Thống kê theo danh mục
                document.add(new Paragraph("THỐNG KÊ THEO DANH MỤC", headingFont));
                document.add(Chunk.NEWLINE);

                if (statistics.getCategorySales().isEmpty()) {
                    document.add(new Paragraph("Không có dữ liệu", normalFont));
                } else {
                    PdfPTable categoriesTable = new PdfPTable(6);
                    categoriesTable.setWidthPercentage(100);

                    // Header
                    PdfPCell headerCell1 = new PdfPCell(new Phrase("STT", boldFont));
                    PdfPCell headerCell2 = new PdfPCell(new Phrase("Danh mục", boldFont));
                    PdfPCell headerCell3 = new PdfPCell(new Phrase("Số lượng", boldFont));
                    PdfPCell headerCell4 = new PdfPCell(new Phrase("Doanh thu", boldFont));
                    PdfPCell headerCell5 = new PdfPCell(new Phrase("Lợi nhuận", boldFont));
                    PdfPCell headerCell6 = new PdfPCell(new Phrase("Tỷ lệ (%)", boldFont));

                    categoriesTable.addCell(headerCell1);
                    categoriesTable.addCell(headerCell2);
                    categoriesTable.addCell(headerCell3);
                    categoriesTable.addCell(headerCell4);
                    categoriesTable.addCell(headerCell5);
                    categoriesTable.addCell(headerCell6);

                    // Data
                    int index = 1;
                    for (CategorySalesDTO category : statistics.getCategorySales()) {
                        categoriesTable.addCell(new PdfPCell(new Phrase(String.valueOf(index++), normalFont)));
                        categoriesTable.addCell(new PdfPCell(new Phrase(category.getName(), normalFont)));
                        categoriesTable
                                .addCell(new PdfPCell(
                                        new Phrase(String.valueOf(category.getQuantitySold()), normalFont)));
                        categoriesTable
                                .addCell(new PdfPCell(new Phrase(formatCurrency(category.getRevenue()), normalFont)));
                        categoriesTable
                                .addCell(new PdfPCell(new Phrase(formatCurrency(category.getProfit()), normalFont)));
                        categoriesTable.addCell(new PdfPCell(
                                new Phrase(String.format("%.2f", category.getPercentageOfTotal()), normalFont)));
                    }

                    document.add(categoriesTable);
                }

                // Thêm ngày tạo báo cáo
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
                Paragraph dateCreated = new Paragraph("Báo cáo được tạo ngày: " +
                        LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont);
                dateCreated.setAlignment(Element.ALIGN_RIGHT);
                document.add(dateCreated);

                return filePath;
            } catch (DocumentException | IOException e) {
                log.error("Lỗi khi tạo báo cáo PDF", e);
                throw new RuntimeException("Không thể tạo báo cáo PDF: " + e.getMessage());
            } finally {
                if (document != null && document.isOpen()) {
                    document.close();
                }
            }
        }
        return folderPath;
    }

    // Phương thức hỗ trợ
    private BigDecimal calculateTotalCost(List<BillDetail> billDetails) {
        BigDecimal totalCost = BigDecimal.ZERO;

        for (BillDetail detail : billDetails) {
            Product product = detail.getProduct();
            BigDecimal importPrice = product.getPrice();
            BigDecimal quantity = new BigDecimal(detail.getQuantity());

            totalCost = totalCost.add(importPrice.multiply(quantity));
        }

        return totalCost;
    }

    private List<ProductSalesDTO> getTopSellingProducts(List<BillDetail> billDetails, int limit) {
        // Nhóm sản phẩm theo ID
        Map<Long, ProductSalesDTO> productMap = new HashMap<>();

        for (BillDetail detail : billDetails) {
            Product product = detail.getProduct();
            Long productId = product.getId();

            ProductSalesDTO productStats = productMap.getOrDefault(productId,
                    ProductSalesDTO.builder()
                            .id(productId)
                            .name(product.getName())
                            .quantitySold(0)
                            .revenue(BigDecimal.ZERO)
                            .profit(BigDecimal.ZERO)
                            .build());

            // Cập nhật số liệu
            int quantity = detail.getQuantity();
            BigDecimal salePrice = detail.getSubPrice();
            BigDecimal revenue = salePrice.multiply(new BigDecimal(quantity));
            BigDecimal cost = product.getPrice().multiply(new BigDecimal(quantity));
            BigDecimal profit = revenue.subtract(cost);

            productStats.setQuantitySold(productStats.getQuantitySold() + quantity);
            productStats.setRevenue(productStats.getRevenue().add(revenue));
            productStats.setProfit(productStats.getProfit().add(profit));

            productMap.put(productId, productStats);
        }

        // Sắp xếp theo số lượng bán và lấy top N
        return productMap.values().stream()
                .sorted(Comparator.comparing(ProductSalesDTO::getQuantitySold).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    private List<CategorySalesDTO> getCategorySales(List<BillDetail> billDetails, BigDecimal totalRevenue) {
        // Nhóm sản phẩm theo danh mục
        Map<Long, CategorySalesDTO> categoryMap = new HashMap<>();

        for (BillDetail detail : billDetails) {
            Product product = detail.getProduct();
            Category category = product.getCategory();
            Long categoryId = category.getId();

            CategorySalesDTO categoryStats = categoryMap.getOrDefault(categoryId,
                    CategorySalesDTO.builder()
                            .id(categoryId)
                            .name(category.getName())
                            .quantitySold(0)
                            .revenue(BigDecimal.ZERO)
                            .profit(BigDecimal.ZERO)
                            .percentageOfTotal(0)
                            .build());

            // Cập nhật số liệu
            int quantity = detail.getQuantity();
            BigDecimal salePrice = detail.getSubPrice();
            BigDecimal revenue = salePrice.multiply(new BigDecimal(quantity));
            BigDecimal cost = product.getPrice().multiply(new BigDecimal(quantity));
            BigDecimal profit = revenue.subtract(cost);

            categoryStats.setQuantitySold(categoryStats.getQuantitySold() + quantity);
            categoryStats.setRevenue(categoryStats.getRevenue().add(revenue));
            categoryStats.setProfit(categoryStats.getProfit().add(profit));

            categoryMap.put(categoryId, categoryStats);
        }

        // Tính phần trăm doanh thu trên tổng doanh thu
        if (totalRevenue.compareTo(BigDecimal.ZERO) > 0) {
            for (CategorySalesDTO category : categoryMap.values()) {
                double percentage = category.getRevenue()
                        .multiply(new BigDecimal(100))
                        .divide(totalRevenue, 2, RoundingMode.HALF_UP)
                        .doubleValue();
                category.setPercentageOfTotal(percentage);
            }
        }

        // Sắp xếp theo doanh thu
        return categoryMap.values().stream()
                .sorted(Comparator.comparing(CategorySalesDTO::getRevenue).reversed())
                .collect(Collectors.toList());
    }

    private Map<String, Integer> getMonthlySales(List<Bill> bills, int year, int startMonth, int endMonth) {
        Map<String, Integer> monthlySales = new HashMap<>();

        // Khởi tạo tất cả các tháng với giá trị 0
        for (int i = startMonth; i <= endMonth; i++) {
            String monthKey = year + "-" + (i < 10 ? "0" + i : i);
            monthlySales.put(monthKey, 0);
        }

        // Tính số lượng sản phẩm bán theo tháng
        for (Bill bill : bills) {
            LocalDate date = bill.getCreatedAt().toLocalDate();
            if (date.getYear() == year && date.getMonthValue() >= startMonth && date.getMonthValue() <= endMonth) {
                String monthKey = year + "-"
                        + (date.getMonthValue() < 10 ? "0" + date.getMonthValue() : date.getMonthValue());

                int productCount = bill.getBillDetails().stream()
                        .mapToInt(BillDetail::getQuantity)
                        .sum();

                monthlySales.put(monthKey, monthlySales.getOrDefault(monthKey, 0) + productCount);
            }
        }

        return monthlySales;
    }

    private Map<Integer, Integer> getQuarterlySales(List<Bill> bills) {
        Map<Integer, Integer> quarterlySales = new HashMap<>();

        // Khởi tạo tất cả các quý với giá trị 0
        for (int i = 1; i <= 4; i++) {
            quarterlySales.put(i, 0);
        }

        // Tính số lượng sản phẩm bán theo quý
        for (Bill bill : bills) {
            LocalDate date = bill.getCreatedAt().toLocalDate();
            int quarter = (date.getMonthValue() - 1) / 3 + 1;

            int productCount = bill.getBillDetails().stream()
                    .mapToInt(BillDetail::getQuantity)
                    .sum();

            quarterlySales.put(quarter, quarterlySales.getOrDefault(quarter, 0) + productCount);
        }

        return quarterlySales;
    }

    private void addTableRow(PdfPTable table, String label, String value, Font normalFont, Font boldFont) {
        PdfPCell cell1 = new PdfPCell(new Phrase(label, normalFont));
        cell1.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase(value, boldFont));
        cell2.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell2);
    }

    private String formatCurrency(BigDecimal amount) {
        return String.format("%,.0f VNĐ", amount);
    }
}
