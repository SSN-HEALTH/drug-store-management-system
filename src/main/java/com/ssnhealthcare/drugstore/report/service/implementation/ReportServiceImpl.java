package com.ssnhealthcare.drugstore.report.service.implementation;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.UnitValue;
import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import com.ssnhealthcare.drugstore.drug.repository.DrugRepository;
import com.ssnhealthcare.drugstore.exception.BusinessException;
import com.ssnhealthcare.drugstore.inventory.repository.InventoryRepository;
import com.ssnhealthcare.drugstore.order.repository.OrderItemRepository;
import com.ssnhealthcare.drugstore.order.repository.OrderRepository;
import com.ssnhealthcare.drugstore.report.Dto.FinancialReportDto;
import com.ssnhealthcare.drugstore.report.Dto.ReportSection;
import com.ssnhealthcare.drugstore.report.Dto.SalesReportDto;
import com.ssnhealthcare.drugstore.report.Dto.StockReportDto;
import com.ssnhealthcare.drugstore.report.service.ReportService;
import com.ssnhealthcare.drugstore.sale.repository.SaleItemRepository;
import com.ssnhealthcare.drugstore.sale.repository.SaleRepository;
import com.itextpdf.layout.element.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor

public class ReportServiceImpl implements ReportService {
    private final OrderItemRepository orderItemRepository;

    private  final InventoryRepository inventoryRepository;

    private  final SaleItemRepository saleItemRepository;

    private final OrderRepository orderRepository;

    private  final SaleRepository saleRepository;


    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 50;
    @Override
    public Page<SalesReportDto> generateSaleReport(
            LocalDate fromDate,
            LocalDate toDate,
            int page,
            int size
    ) {
        if (fromDate.isAfter(toDate)) {
            throw new BusinessException("From date cannot be after To date");
        }

        int pageNo = Math.max(page, DEFAULT_PAGE);
        int pageSize = Math.min(size, MAX_SIZE);

        List<SalesReportDto> report =
                saleItemRepository.getSalesReport(
                        fromDate.atStartOfDay(),
                        toDate.atTime(23, 59, 59)
                );

        report.forEach(r -> {
            Integer stock =
                    inventoryRepository.getAvailableStockByDrugName(r.getDrugName());
            r.setAvailableStock(stock != null ? stock : 0);
        });

        return paginate(report, pageNo, pageSize);
    }

    @Override
    public Page<StockReportDto> generateStockReport(int page, int size) {

        int pageNo = Math.max(page, DEFAULT_PAGE);
        int pageSize = Math.min(size, MAX_SIZE);

        List<StockReportDto> report =
                inventoryRepository.generateStockReport();

        return paginate(report, pageNo, pageSize);
    }


    @Override
    public FinancialReportDto generateFinancialReport(LocalDate fromDate, LocalDate toDate)
    {
        if (fromDate.isAfter(toDate)) {
            throw new BusinessException("From Date cannot be after To Date");
        }

        LocalDateTime from = fromDate.atStartOfDay();
        LocalDateTime to = toDate.atTime(23, 59, 59);

        Long totalOrders = orderRepository.countByStatusAndOrderDateBetween(
                OrderStatus.COMPLETED, from, to
        );

        Long totalItemsSold = saleItemRepository.sumQuantityByOrderStatusAndDate(
                OrderStatus.COMPLETED, from, to
        );

        BigDecimal totalRevenue = saleItemRepository.sumRevenueByOrderStatusAndDate(
                OrderStatus.COMPLETED, from, to
        );

        return new FinancialReportDto(
                totalOrders,
                totalItemsSold,
                totalRevenue != null ? totalRevenue : BigDecimal.ZERO
        );


    }

    @Override
    public ResponseEntity<?> downloadSalesReport(LocalDate fromDate, LocalDate toDate, int page, int size, String format) {
        try {
            Page<SalesReportDto> report = generateSaleReport(fromDate, toDate, page, size);
            ReportSection section = new ReportSection(
                    "Sales Report",
                    List.of("Drug Name", "Total Quantity Sold", "Total Revenue", "Available Stock"),
                    report.getContent().stream()
                            .map(r -> List.of(
                                    r.getDrugName(),
                                    String.valueOf(r.getTotalQuantitySold()),
                                    r.getTotalRevenue().toString(),
                                    String.valueOf(r.getAvailableStock())
                            )).toList()
            );
            return generateFile(section, "sales_report", format, fromDate, toDate);
        } catch (Exception e) {
            throw new BusinessException("Failed to generate sales report: " + e.getMessage());
        }
    }



    @Override
    public ResponseEntity<?> downloadStockReport(int page, int size, String format) {
        try {
            Page<StockReportDto> report = generateStockReport(page, size);
            LocalDate fromDate = LocalDate.now(); // or default/custom date
            LocalDate toDate = LocalDate.now();

            ReportSection section = new ReportSection(
                    "Stock Report",
                    List.of("Drug Name", "Batch Number", "Quantity", "Sold Quantity"),
                    report.getContent().stream()
                            .map(s -> List.of(
                                    s.getDrugName(),
                                    s.getBatchNumber(),
                                    String.valueOf(s.getQuantity()),
                                    String.valueOf(s.getSoldQuantity())
                            )).toList()
            );
            return generateFile(section, "stock_report", format, fromDate, toDate);
        } catch (Exception e) {
            throw new BusinessException("Failed to generate stock report: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> downloadFinancialReport(LocalDate fromDate, LocalDate toDate, String format) {
        try {
            FinancialReportDto financial = generateFinancialReport(fromDate, toDate);

            ReportSection section = new ReportSection(
                    "Financial Report",
                    List.of("Metric", "Value"),
                    List.of(
                            List.of("Total Orders", String.valueOf(financial.getTotalOrders())),
                            List.of("Total Items Sold", String.valueOf(financial.getTotalItemsSold())),
                            List.of("Total Revenue", financial.getTotalRevenue().toString())
                    )
            );

            return generateFile(section, "financial_report", format, fromDate, toDate);
        } catch (Exception e) {
            throw new BusinessException("Failed to generate financial report: " + e.getMessage());
        }
    }

    private ResponseEntity<?> generateFile(ReportSection section, String filename, String format,
                                           LocalDate fromDate, LocalDate toDate) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        switch (format.toLowerCase()) {
            case "pdf":
                output.write(ReportFileGenerator.generatePdf(section.title(), section.headers(), section.rows(),
                        fromDate, toDate));
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + ".pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(new ByteArrayResource(output.toByteArray()));
            case "csv":
                output.write(ReportFileGenerator.generateCsv(section.title(), section.headers(), section.rows(),
                        fromDate, toDate));
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + ".csv")
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(new ByteArrayResource(output.toByteArray()));
            case "txt":
                output.write(ReportFileGenerator.generateTxt(section.title(), section.headers(), section.rows(),
                        fromDate, toDate));
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + ".txt")
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(new ByteArrayResource(output.toByteArray()));
            default:
                throw new BusinessException("Unsupported format: " + format);
        }
    }


    private <T> Page<T> paginate(List<T> data, int page, int size) {
        int start = Math.min(page * size, data.size());
        int end = Math.min(start + size, data.size());
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(data.subList(start, end), pageable, data.size());
    }
    }
