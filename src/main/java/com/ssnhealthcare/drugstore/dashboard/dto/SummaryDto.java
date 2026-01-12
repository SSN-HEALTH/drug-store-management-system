package com.ssnhealthcare.drugstore.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SummaryDto
{
    private BigDecimal totalSales;
    private StockSummaryDto stockSummary;
}
