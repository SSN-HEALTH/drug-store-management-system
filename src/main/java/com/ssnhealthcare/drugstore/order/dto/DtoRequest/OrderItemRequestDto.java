package com.ssnhealthcare.drugstore.order.dto.DtoRequest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemRequestDto {
    @NotNull
    private Long drugId;
    @Min(1)
    private Integer quantity;
    @NotNull
    private BigDecimal price;
}
