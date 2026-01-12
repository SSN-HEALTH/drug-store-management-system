package com.ssnhealthcare.drugstore.dashboard.controller;

import com.ssnhealthcare.drugstore.dashboard.dto.RecentOrderDto;
import com.ssnhealthcare.drugstore.dashboard.dto.StockSummaryDto;
import com.ssnhealthcare.drugstore.dashboard.dto.SummaryDto;
import com.ssnhealthcare.drugstore.dashboard.dto.TopSellingDrugDto;
import com.ssnhealthcare.drugstore.dashboard.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@AllArgsConstructor
public class DashboardController
{

    private final DashboardService dashboardService;
    //Summary (Total Sales + Stock)
    @GetMapping("/summary")
    public ResponseEntity<SummaryDto> getSummary()
    {
        return ResponseEntity.ok(dashboardService.getSummary());
    }
    // Recent Orders
    @GetMapping("/recent-orders")
    public ResponseEntity<Page<RecentOrderDto>> getRecentOrders(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(dashboardService.getRecentOrders(pageable));
    }
    @GetMapping("/top-selling-drugs")
    public ResponseEntity<Page<TopSellingDrugDto>> getTopSellingDrugs(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                dashboardService.getTopSellingDrugs(startDate, endDate, page, size)
        );
    }

    @GetMapping("/stock-levels")
    public ResponseEntity<StockSummaryDto> getStockLevels() {
        return ResponseEntity.ok(dashboardService.getStockLevels());
    }
}
