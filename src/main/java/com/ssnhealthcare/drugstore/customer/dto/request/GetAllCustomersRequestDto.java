package com.ssnhealthcare.drugstore.customer.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetAllCustomersRequestDto {

    @NotNull
    @Min(0)
    private Integer pageNumber;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer size;
}

