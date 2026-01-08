package com.ssnhealthcare.drugstore.returns.dto.requestdto;

import com.ssnhealthcare.drugstore.common.enums.ReturnReason;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateReturnRequestDTO {

    @NotNull(message = "Inventory id is required")
    private Long inventoryId;

    @NotNull(message = "Return reason is required")
    private ReturnReason reason;

    @NotNull(message = "Return quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}