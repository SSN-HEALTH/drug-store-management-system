package com.ssnhealthcare.drugstore.sale.Dto.DtoRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaleCreateRequestDto {
    @NotNull(message = "order id is required")
    private Long orderId;
    @NotNull(message = "processed by userid is required")
    private Long processedByUserId;
    @NotNull(message = "payment mode is required")
    private String paymentMode;

}
