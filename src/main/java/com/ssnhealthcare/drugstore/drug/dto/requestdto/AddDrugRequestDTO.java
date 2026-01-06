package com.ssnhealthcare.drugstore.drug.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddDrugRequestDTO {

    @NotBlank(message = "Drug name is required")
    private String drugName;

    @NotBlank(message = "Composition is required")
    private String composition;

    @NotBlank(message = "Dosage is required")
    private String dosage;

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @NotNull(message = "Category id is required")
    private Long categoryId;
}
