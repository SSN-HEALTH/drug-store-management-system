package com.ssnhealthcare.drugstore.report.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SalesReportDto {
    private String drugName;
    private Long totalQuantitySold;
    private BigDecimal totalRevenue;
}
