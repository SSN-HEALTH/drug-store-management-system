package com.ssnhealthcare.drugstore.report.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockReportDto {
    private String drugName;
    private Long stockQuantity;
    private Long soldQuantity;
}
