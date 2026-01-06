package com.ssnhealthcare.drugstore.report.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
public class FinancialReportDto {
    private Long totalOrders;
    private Long totalItemsSold;
    private BigDecimal totalRevenue;
    public FinancialReportDto() {
        if (totalOrders == null || totalOrders < 0)
            throw new IllegalArgumentException("Total orders cannot be null or negative");

        if (totalItemsSold == null || totalItemsSold < 0)
            throw new IllegalArgumentException("Total items sold cannot be null or negative");

        if (totalRevenue == null || totalRevenue.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Total revenue cannot be null or negative");
    }
}
