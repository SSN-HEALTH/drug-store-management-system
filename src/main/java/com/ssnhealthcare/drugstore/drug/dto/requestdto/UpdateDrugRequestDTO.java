package com.ssnhealthcare.drugstore.drug.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateDrugRequestDTO {

    @NotNull(message = "Drug id is required")
    private Long id;

    @NotBlank(message = "Drug name is required")
    @Size(max = 100, message = "Drug name must not exceed 100 characters")
    private String drugName;

    @Size(max = 200, message = "Composition must not exceed 200 characters")
    private String composition;

    @Size(max = 50, message = "Dosage must not exceed 50 characters")
    private String dosage;

    @NotBlank(message = "Manufacturer is required")
    @Size(max = 100, message = "Manufacturer must not exceed 100 characters")
    private String manufacturer;

    @NotNull(message = "Category id is required")
    private Long categoryId;
}
