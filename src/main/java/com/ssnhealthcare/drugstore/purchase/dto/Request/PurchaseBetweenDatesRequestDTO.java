package com.ssnhealthcare.drugstore.purchase.dto.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;


import java.time.LocalDate;

@Data
public class PurchaseBetweenDatesRequestDTO {

    @NotNull (message = "From date cannot be empty")
    @PastOrPresent
    private LocalDate fromDate;

    @NotNull(message = "To date cannot be empty")
    @PastOrPresent
    private LocalDate toDate;

//    @NotBlank (message = "Created date cannot be empty")
//    @PastOrPresent
//    private LocalDate creationDate;

    @Min(value = 0, message = "Page number must be 0 or greater")
    private Integer pageNumber = 0;

    @Min(value = 1, message = "Page size must be at least 1")
    private Integer size = 10;

}
