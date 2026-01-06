package com.ssnhealthcare.drugstore.report.controller;

import com.ssnhealthcare.drugstore.report.Dto.FinancialReportDto;
import com.ssnhealthcare.drugstore.report.Dto.SalesReportDto;
import com.ssnhealthcare.drugstore.report.Dto.StockReportDto;
import com.ssnhealthcare.drugstore.report.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    @GetMapping("/sales")
    public ResponseEntity<Page<SalesReportDto>> getSalesReport(@RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)LocalDate fromDate,
                                                               @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)LocalDate toDate)
    {
        return ResponseEntity.ok(reportService.generateSaleReport(fromDate,toDate));
    }
    @GetMapping("/stock")
    public ResponseEntity<Page<StockReportDto>> getStockReport()
    {
        return ResponseEntity.ok(reportService.generateStockReport());
    }
    @GetMapping("/financial")
    public ResponseEntity<FinancialReportDto> getFinancialReport(@RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)LocalDate fromDate,
                                                                 @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)LocalDate toDate)
    {
        return ResponseEntity.ok(reportService.generateFinancialReport(fromDate,toDate));
    }


}
