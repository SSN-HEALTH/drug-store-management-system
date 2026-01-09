package com.ssnhealthcare.drugstore.distributor.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DistributorRequestDTO {

    @NotNull (message = "Page number cannot be empty")
    @Min(value = 0, message = "Page Number must be 0 or greater")
    private int pageNumber;

    @NotNull (message = "Size cannot be empty")
    @Min(value = 1, message = "size must be at least 1")
    @Max(value = 100, message = "size must be not exceed 100")
    private int size;
}
