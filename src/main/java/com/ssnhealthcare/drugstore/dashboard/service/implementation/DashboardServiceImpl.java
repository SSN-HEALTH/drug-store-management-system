package com.ssnhealthcare.drugstore.dashboard.service.implementation;

import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import com.ssnhealthcare.drugstore.dashboard.dto.RecentOrderDto;
import com.ssnhealthcare.drugstore.dashboard.dto.StockSummaryDto;
import com.ssnhealthcare.drugstore.dashboard.dto.SummaryDto;
import com.ssnhealthcare.drugstore.dashboard.dto.TopSellingDrugDto;
import com.ssnhealthcare.drugstore.dashboard.service.DashboardService;
import com.ssnhealthcare.drugstore.exception.ResourceNotFoundException;
import com.ssnhealthcare.drugstore.inventory.repository.InventoryRepository;
import com.ssnhealthcare.drugstore.order.repository.OrderItemRepository;
import com.ssnhealthcare.drugstore.order.repository.OrderRepository;
import com.ssnhealthcare.drugstore.sale.repository.SaleRepository;
import jakarta.persistence.Cacheable;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService
{


    private final SaleRepository saleRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    @Override
    public SummaryDto getSummary()
    {
        LocalDateTime startDate = LocalDate.now()
                .minusMonths(1)
                .atStartOfDay();

        LocalDateTime endDate = LocalDateTime.now();

        BigDecimal totalSales = saleRepository.calculateTotalSales(
                startDate,
                endDate,
                OrderStatus.COMPLETED
        );

        // Calculate stock values
        long totalStock = inventoryRepository.getTotalStockQuantity();
        long lowStock = inventoryRepository.getLowStockCount();
        long outOfStock = inventoryRepository.getOutOfStockCount();
        long nearExpiry = inventoryRepository.getNearExpiryCount(
                LocalDate.now().plusDays(30)
        );

        StockSummaryDto stockSummary = new StockSummaryDto(
                totalStock,
                lowStock,
                outOfStock,
                nearExpiry
        );

        return new SummaryDto(totalSales, stockSummary);
    }

    @Override
    public Page<RecentOrderDto> getRecentOrders(Pageable pageable)
    {
        LocalDateTime fromDate = LocalDateTime.now().minusDays(7);

        return orderRepository.findRecentOrders(fromDate, pageable);
    }






    @Override
    public Page<TopSellingDrugDto> getTopSellingDrugs(String start, String end, int page, int size) {
        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(start);
            endDate = LocalDate.parse(end);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        Pageable pageable = PageRequest.of(page, size);

        return orderItemRepository.findTopSellingDrugs(
                startDateTime,
                endDateTime,
                pageable
        );
    }

    @Override
    public StockSummaryDto getStockLevels() {
        long totalStock = inventoryRepository.getTotalStockQuantity();
        long lowStock = inventoryRepository.getLowStockCount();
        long outOfStock = inventoryRepository.getOutOfStockCount();

        // Industry standard: near expiry = next 30 days
        LocalDate nearExpiryDate = LocalDate.now().plusDays(30);
        long nearExpiry = inventoryRepository.getNearExpiryCount(nearExpiryDate);

        return new StockSummaryDto(
                totalStock,
                lowStock,
                outOfStock,
                nearExpiry
        );

    }


}
