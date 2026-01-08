package com.ssnhealthcare.drugstore.report.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Getter
public class StockReportDto {
    private String drugName;
    private String batchNumber;
    private Integer quantity; // match the type from Inventory
    private Long soldQuantity; // match the type of SUM(...)

    // Constructor that matches the JPQL query parameters exactly
    public StockReportDto(String drugName, String batchNumber, Integer quantity, Long soldQuantity) {
        this.drugName = drugName;
        this.batchNumber = batchNumber;
        this.quantity = quantity;
        this.soldQuantity = soldQuantity;
    }
    // getters and setters
    public String getDrugName() { return drugName; }
    public String getBatchNumber() { return batchNumber; }
    public Integer getQuantity() { return quantity; }
    public Long getSoldQuantity() { return soldQuantity; }
}
