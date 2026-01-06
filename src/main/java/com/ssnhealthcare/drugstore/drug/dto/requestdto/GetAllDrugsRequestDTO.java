package com.ssnhealthcare.drugstore.drug.dto.requestdto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetAllDrugsRequestDTO {

    @NotNull(message = "Page number is required")
    @Min(value = 0, message = "Page number must be 0 or greater")
    private Integer pageNumber;

    @NotNull(message = "Size is required")
    @Min(value = 1, message = "Size must be at least 1")
    @Max(value = 100, message = "Size must not exceed 100")
    private Integer size;
}
