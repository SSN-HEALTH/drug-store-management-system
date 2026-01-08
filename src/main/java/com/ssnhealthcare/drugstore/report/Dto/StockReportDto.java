package com.ssnhealthcare.drugstore.report.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Getter
public class StockReportDto {

    private final String drugName;
    private final String batchNumber;
    private final Long availableStock;
    private final Long totalSoldQuantity;

    public StockReportDto(String drugName,
                          String batchNumber,
                          Long availableStock,
                          Long totalSoldQuantity) {
        this.drugName = drugName;
        this.batchNumber = batchNumber;
        this.availableStock = availableStock;
        this.totalSoldQuantity = totalSoldQuantity;
    }
}
