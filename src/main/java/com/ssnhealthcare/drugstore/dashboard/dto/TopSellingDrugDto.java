package com.ssnhealthcare.drugstore.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopSellingDrugDto
{
    private String drugName;
    private long soldQuantity;
}
