package com.ssnhealthcare.drugstore.report.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class StockReportDto {

    private final String drugName;
    private final Long availableStock;  // changed from Integer to Long
    private final Long totalSoldQuantity;

    public StockReportDto(String drugName,
                          Long availableStock,
                          Long totalSoldQuantity) {
        this.drugName = drugName;
        this.availableStock = availableStock;
        this.totalSoldQuantity = totalSoldQuantity;
    }
}
