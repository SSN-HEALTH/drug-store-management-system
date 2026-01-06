package com.ssnhealthcare.drugstore.inventory.dto.responsedto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InventoryResponseDTO {

    private Long inventoryId;
    private Long drugId;
    private String drugName;
    private Integer quantity;
    private Integer reorderLevel;
    private LocalDate expiryDate;
    private String batchNumber;
}
