package com.ssnhealthcare.drugstore.report.service;

import com.ssnhealthcare.drugstore.report.Dto.FinancialReportDto;
import com.ssnhealthcare.drugstore.report.Dto.SalesReportDto;
import com.ssnhealthcare.drugstore.report.Dto.StockReportDto;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface ReportService {
    Page<SalesReportDto> generateSaleReport(LocalDate fromDate, LocalDate toDate);

    Page<StockReportDto> generateStockReport();

     FinancialReportDto generateFinancialReport(LocalDate fromDate, LocalDate toDate);
}
