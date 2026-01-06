package com.ssnhealthcare.drugstore.drug.dto.responsedto;

import lombok.Data;

@Data
public class DrugResponseDTO {

    private Long drugId;
    private String drugName;
    private String composition;
    private String dosage;
    private String manufacturer;
    private String categoryName;
}
