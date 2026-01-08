package com.ssnhealthcare.drugstore.report.service.implementation;

import com.ssnhealthcare.drugstore.drug.repository.DrugRepository;
import com.ssnhealthcare.drugstore.exception.BusinessException;
import com.ssnhealthcare.drugstore.inventory.repository.InventoryRepository;
import com.ssnhealthcare.drugstore.order.repository.OrderItemRepository;
import com.ssnhealthcare.drugstore.order.repository.OrderRepository;
import com.ssnhealthcare.drugstore.report.Dto.FinancialReportDto;
import com.ssnhealthcare.drugstore.report.Dto.SalesReportDto;
import com.ssnhealthcare.drugstore.report.Dto.StockReportDto;
import com.ssnhealthcare.drugstore.report.service.ReportService;
import com.ssnhealthcare.drugstore.sale.repository.SaleItemRepository;
import com.ssnhealthcare.drugstore.sale.repository.SaleRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public Page<SalesReportDto> generateSaleReport(LocalDate fromDate, LocalDate toDate)
    {
        if (fromDate.isAfter(toDate)) {
            throw new RuntimeException("From date cannot be after To date");
        }

        List<SalesReportDto> report = saleItemRepository.getSalesReport(
                fromDate.atStartOfDay(),
                toDate.atTime(23, 59, 59)
        );

        // Set available stock for each drug
        report.forEach(r -> {
            Integer stock = inventoryRepository.getAvailableStockByDrugName(r.getDrugName());
            r.setAvailableStock(stock != null ? stock : 0);
        });

        // Pagination if needed
        return new PageImpl<>(report);
    }

    @Override
    public Page<StockReportDto> generateStockReport()
    {

        List<StockReportDto> report = inventoryRepository.generateStockReport();
        return paginate(report, DEFAULT_PAGE, DEFAULT_SIZE);
    }

    @Override
    public FinancialReportDto generateFinancialReport(LocalDate fromDate, LocalDate toDate)
    {
        if (fromDate.isAfter(toDate)) {
            throw new BusinessException("From Date cannot be after To Date");
        }

        LocalDateTime from = fromDate.atStartOfDay();
        LocalDateTime to = toDate.atTime(23, 59, 59);

        Long totalOrders =
                orderRepository.countCompletedOrders(from, to);

        Long totalItemsSold =
                saleItemRepository.getTotalItemsSold(from, to);

        BigDecimal totalRevenue =
                saleRepository.getTotalRevenue(from, to);

        return new FinancialReportDto(
                totalOrders,
                totalItemsSold,
                totalRevenue);

    }

    private <T> Page<T> paginate(List<T> data, int page, int size)
    {

        int start = Math.min(page * size, data.size());
        int end = Math.min(start + size, data.size());

        return new PageImpl<>(data.subList(start, end), PageRequest.of(page, size), data.size());
    }
}
