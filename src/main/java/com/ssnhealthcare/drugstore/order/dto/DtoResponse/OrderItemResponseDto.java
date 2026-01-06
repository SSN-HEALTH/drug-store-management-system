package com.ssnhealthcare.drugstore.order.dto.DtoResponse;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponseDto {

    private Long drugId;
    private String drugName;
    private  Integer quantity;
    private BigDecimal price;

}
