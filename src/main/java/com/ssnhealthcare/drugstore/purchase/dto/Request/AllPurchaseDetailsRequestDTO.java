package com.ssnhealthcare.drugstore.purchase.dto.Request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllPurchaseDetailsRequestDTO {

    @NotBlank(message = "Page number cannot be empty")
    @NumberFormat
    private int pageNumber;
    @NotBlank(message = "Size cannot be empty")
    @NumberFormat
    private int size;
}
