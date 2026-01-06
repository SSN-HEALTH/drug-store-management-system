package com.ssnhealthcare.drugstore.inventory.service;

import com.ssnhealthcare.drugstore.inventory.dto.requestdto.AddInventoryRequestDTO;
import com.ssnhealthcare.drugstore.inventory.dto.requestdto.InventoryPageRequestDTO;
import com.ssnhealthcare.drugstore.inventory.dto.requestdto.ReduceStockRequestDTO;
import com.ssnhealthcare.drugstore.inventory.dto.responsedto.InventoryResponseDTO;
import com.ssnhealthcare.drugstore.inventory.dto.responsedto.ReduceStockResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {
    InventoryResponseDTO addInventory(@Valid AddInventoryRequestDTO dto);

    ReduceStockResponseDTO reduceStock(@Valid ReduceStockRequestDTO dto);

    InventoryResponseDTO getInventoryByDrug(Long drugId);

    List<InventoryResponseDTO> getLowStockInventories();

    List<InventoryResponseDTO> getExpiringInventories(Integer days);

    Page<InventoryResponseDTO> getAllInventory(@Valid InventoryPageRequestDTO dto);
}
