package com.ssnhealthcare.drugstore.dashboard.service;

import com.ssnhealthcare.drugstore.dashboard.dto.RecentOrderDto;
import com.ssnhealthcare.drugstore.dashboard.dto.SummaryDto;
import com.ssnhealthcare.drugstore.dashboard.dto.TopSellingDrugDto;
import com.ssnhealthcare.drugstore.dashboard.dto.StockSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DashboardService {

    SummaryDto getSummary();

    Page<RecentOrderDto> getRecentOrders(Pageable pageable);



    Page<TopSellingDrugDto> getTopSellingDrugs(String start, String end, int page, int size);

    StockSummaryDto getStockLevels();
}
