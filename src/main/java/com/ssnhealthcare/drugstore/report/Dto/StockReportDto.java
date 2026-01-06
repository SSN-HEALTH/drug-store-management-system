package com.ssnhealthcare.drugstore.report.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class StockReportDto {

    private final String drugName;
    private final Integer availableStock;
    private final Long totalSoldQuantity;
    public StockReportDto(String drugName,
                          Integer availableStock,
                          Long totalSoldQuantity) {
        this.drugName = drugName;
        this.availableStock = availableStock;
        this.totalSoldQuantity = totalSoldQuantity;
    }
}
