package com.ssnhealthcare.drugstore.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockSummaryDto {

    private long totalStockQuantity;
    private long lowStockCount;
    private long outOfStockCount;
    private long nearExpiryCount;
}
