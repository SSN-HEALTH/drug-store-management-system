package com.ssnhealthcare.drugstore.purchase.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderCancelRequestDTO {

    @NotBlank(message = "Purchase Order Id cannot be blank ")
    @NumberFormat
    private Long purchaseOrderId;
}
