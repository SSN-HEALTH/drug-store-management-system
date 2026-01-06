package com.ssnhealthcare.drugstore.report.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data

@NoArgsConstructor
public class SalesReportDto {
    private String drugName;
    private Long totalQuantitySold;
    private BigDecimal totalRevenue;
    private Integer availableStock;
    public SalesReportDto(String drugName,
                          Long totalQuantitySold,
                          BigDecimal totalRevenue) {
        this.drugName = drugName;
        this.totalQuantitySold = totalQuantitySold;
        this.totalRevenue = totalRevenue;
    }

}
