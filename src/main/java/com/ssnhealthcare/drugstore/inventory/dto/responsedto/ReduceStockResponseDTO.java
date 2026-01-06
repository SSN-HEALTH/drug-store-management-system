package com.ssnhealthcare.drugstore.inventory.dto.responsedto;

import lombok.Data;

@Data
public class ReduceStockResponseDTO {

    private Long drugId;
    private String drugName;
    private Integer oldQuantity;
    private Integer reducedQuantity;
    private Integer remainingQuantity;
    private String batchNumber;
}
