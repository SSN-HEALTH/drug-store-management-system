package com.ssnhealthcare.drugstore.report.service.implementation;

import com.ssnhealthcare.drugstore.drug.repository.DrugRepository;
import com.ssnhealthcare.drugstore.exception.BusinessException;
import com.ssnhealthcare.drugstore.order.repository.OrderItemRepository;
import com.ssnhealthcare.drugstore.report.Dto.FinancialReportDto;
import com.ssnhealthcare.drugstore.report.Dto.SalesReportDto;
import com.ssnhealthcare.drugstore.report.Dto.StockReportDto;
import com.ssnhealthcare.drugstore.report.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final OrderItemRepository orderItemRepository;
    private DrugRepository drugRepository;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 50;
    @Override
    public Page<SalesReportDto> generateSaleReport(LocalDate fromDate, LocalDate toDate)
    {
        if (fromDate.isAfter(toDate)) {
            throw new BusinessException("From date cannot be after To date");
        }

        try {
            List<SalesReportDto> report = orderItemRepository.getSalesReport(fromDate.atStartOfDay(), toDate.atTime(23, 59, 59));

            return paginate(report, DEFAULT_PAGE, DEFAULT_SIZE);

        }
        catch (Exception e)
        {
            throw new BusinessException("Failed to generate sales report");
        }
    }

    @Override
    public Page<StockReportDto> generateStockReport()
    {
        try {
        List<StockReportDto> report = drugRepository.getStockReport();

        return paginate(report,DEFAULT_PAGE,DEFAULT_SIZE);
    } catch (Exception e)
        {
            throw new BusinessException("Failed to Generate the Report");
        }

    }

    @Override
    public FinancialReportDto generateFinancialReport(LocalDate fromDate, LocalDate toDate)
    {
        if(fromDate.isAfter(toDate))
        {
            throw  new BusinessException("From Date cannot be after To date");
        }
        try {
           List<SalesReportDto> sales= orderItemRepository.getSalesReport(fromDate.atStartOfDay(),toDate.atTime(23,59,59));
            long totalItems = sales.stream().mapToLong(SalesReportDto::getTotalQuantitySold).sum();

            BigDecimal totalRevenue = sales.stream()
                            .map(SalesReportDto::getTotalRevenue)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

            return new FinancialReportDto( Long.valueOf(sales.size()), totalItems, totalRevenue);
        }
        catch (Exception e)
        {
            throw new BusinessException("Failed to Generate the Report");
        }

    }

    private <T> Page<T> paginate(List<T> data, int page, int size)
    {

        int start = Math.min(page * size, data.size());
        int end = Math.min(start + size, data.size());

        return new PageImpl<>(data.subList(start, end), PageRequest.of(page, size), data.size());
    }
}
