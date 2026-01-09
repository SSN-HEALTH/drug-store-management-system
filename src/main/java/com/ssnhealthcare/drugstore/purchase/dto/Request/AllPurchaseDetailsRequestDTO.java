package com.ssnhealthcare.drugstore.purchase.dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllPurchaseDetailsRequestDTO {

    @NotNull(message = "Page number cannot be empty")
    @NumberFormat
    private int pageNumber;
    @NotNull(message = "Size cannot be empty")
    @NumberFormat
    private int size;
}
