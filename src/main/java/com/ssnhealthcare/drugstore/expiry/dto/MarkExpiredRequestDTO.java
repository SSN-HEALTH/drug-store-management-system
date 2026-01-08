package com.ssnhealthcare.drugstore.expiry.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MarkExpiredRequestDTO {

    @NotNull
    private Long inventoryId;

    @Min(1)
    private Integer quantity;
}
