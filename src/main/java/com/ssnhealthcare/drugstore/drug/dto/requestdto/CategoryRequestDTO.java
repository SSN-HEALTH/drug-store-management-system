package com.ssnhealthcare.drugstore.drug.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDTO {

    @NotBlank(message = "Category name is required")
    private String name;

    @NotBlank(message = "Category Description is required")
    private String description;
}
