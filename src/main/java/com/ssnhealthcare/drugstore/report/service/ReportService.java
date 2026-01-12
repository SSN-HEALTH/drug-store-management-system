package com.ssnhealthcare.drugstore.report.service;

import com.ssnhealthcare.drugstore.report.Dto.FinancialReportDto;
import com.ssnhealthcare.drugstore.report.Dto.SalesReportDto;
import com.ssnhealthcare.drugstore.report.Dto.StockReportDto;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    // UPDATED: pagination params added
    Page<SalesReportDto> generateSaleReport(LocalDate fromDate, LocalDate toDate, int page, int size);

    Page<StockReportDto> generateStockReport(int page, int size);

    FinancialReportDto generateFinancialReport(LocalDate fromDate, LocalDate toDate);


    ResponseEntity<?> downloadSalesReport(LocalDate fromDate, LocalDate toDate, int page, int size, String format);

    ResponseEntity<?> downloadStockReport(int page, int size, String format);

    ResponseEntity<?> downloadFinancialReport(LocalDate fromDate, LocalDate toDate, String format);
}
