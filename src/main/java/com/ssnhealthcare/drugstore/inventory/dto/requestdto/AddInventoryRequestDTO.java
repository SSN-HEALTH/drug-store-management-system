package com.ssnhealthcare.drugstore.inventory.dto.requestdto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddInventoryRequestDTO {

    @NotNull(message = "Drug id is required")
    private Long id;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Reorder level is required")
    @Min(value = 1, message = "Reorder level must be at least 1")
    private Integer reorderLevel;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;

    @NotNull(message = "Batch number is required")
    private String batchNumber;
}
