package com.ssnhealthcare.drugstore.inventory.dto.requestdto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReduceStockRequestDTO {

    @NotNull(message = "Drug id is required")
    private Long drugId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
