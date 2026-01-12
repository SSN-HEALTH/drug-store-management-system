package com.ssnhealthcare.drugstore.report.controller;

import com.ssnhealthcare.drugstore.report.Dto.FinancialReportDto;
import com.ssnhealthcare.drugstore.report.Dto.SalesReportDto;
import com.ssnhealthcare.drugstore.report.Dto.StockReportDto;
import com.ssnhealthcare.drugstore.report.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
public class ReportController {
    private final ReportService reportService;

    /**
     * SALES REPORT
     * Daily / Monthly
     */
    @GetMapping("/sales")
    public ResponseEntity<Page<SalesReportDto>> getSalesReport(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                reportService.generateSaleReport(fromDate, toDate, page, size)
        );
    }
    /**
     * STOCK REPORT
     */
    @GetMapping("/stock")
    public ResponseEntity<Page<StockReportDto>> getStockReport(int page, int size) {
        return ResponseEntity.ok(
                reportService.generateStockReport(page, size)
        );
    }

    /**
     * FINANCIAL REPORT
     */
    @GetMapping("/financial")
    public ResponseEntity<FinancialReportDto> getFinancialReport(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate
    ) {
        return ResponseEntity.ok(
                reportService.generateFinancialReport(fromDate, toDate)
        );
    }

    /** Sales Report Download */
    @GetMapping("/sales/download")
    public ResponseEntity<?> downloadSalesReport(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "pdf") String format
    ) {
        return reportService.downloadSalesReport(fromDate, toDate, page, size, format);
    }

    /** Stock Report Download */
    @GetMapping("/stock/download")
    public ResponseEntity<?> downloadStockReport(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "pdf") String format
    ) {
        return reportService.downloadStockReport(page, size, format);
    }

    /** Financial Report Download */
    @GetMapping("/financial/download")
    public ResponseEntity<?> downloadFinancialReport(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate,
            @RequestParam(defaultValue = "pdf") String format
    ) {
        return reportService.downloadFinancialReport(fromDate, toDate, format);
    }
}
