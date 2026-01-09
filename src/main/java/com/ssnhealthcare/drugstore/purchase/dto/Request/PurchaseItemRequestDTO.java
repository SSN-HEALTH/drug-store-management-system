package com.ssnhealthcare.drugstore.purchase.dto.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class PurchaseItemRequestDTO {

    @NotNull
    private Long drugId;

    @Min(1)
    private Integer quantity;

    @Positive
    private BigDecimal price;
}
