package com.ssnhealthcare.drugstore.common.mapper;

import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import com.ssnhealthcare.drugstore.returns.dto.responsedto.ReturnResponseDTO;
import com.ssnhealthcare.drugstore.returns.entity.Return;
import org.springframework.stereotype.Component;

@Component
public class ReturnMapper {

    public ReturnResponseDTO toResponse(Return ret) {

        Inventory inventory = ret.getInventory();

        ReturnResponseDTO response = new ReturnResponseDTO();
        response.setReturnId(ret.getReturnId());
        response.setInventoryId(inventory.getInventoryId());
        response.setDrugId(inventory.getDrug().getDrugId());
        response.setDrugName(inventory.getDrug().getDrugName());
        response.setBatchNumber(inventory.getBatchNumber());
        response.setReturnedQuantity(ret.getQuantity());
        response.setRemainingQuantity(inventory.getQuantity());
        response.setReason(ret.getReason());
        response.setReturnDate(ret.getReturnDate());

        return response;
    }
}
