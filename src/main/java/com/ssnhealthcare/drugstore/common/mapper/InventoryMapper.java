package com.ssnhealthcare.drugstore.common.mapper;

import com.ssnhealthcare.drugstore.inventory.dto.responsedto.InventoryResponseDTO;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public InventoryResponseDTO toResponse(Inventory inventory) {

        InventoryResponseDTO response = new InventoryResponseDTO();
        response.setInventoryId(inventory.getInventoryId());
        response.setDrugId(inventory.getDrug().getDrugId());
        response.setDrugName(inventory.getDrug().getDrugName());
        response.setQuantity(inventory.getQuantity());
        response.setReorderLevel(inventory.getReorderLevel());
        response.setExpiryDate(inventory.getExpiryDate());
        response.setBatchNumber(inventory.getBatchNumber());

        return response;
    }
}
