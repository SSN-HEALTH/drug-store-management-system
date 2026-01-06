package com.ssnhealthcare.drugstore.inventory.controller;

import com.ssnhealthcare.drugstore.inventory.dto.requestdto.AddInventoryRequestDTO;
import com.ssnhealthcare.drugstore.inventory.dto.requestdto.InventoryPageRequestDTO;
import com.ssnhealthcare.drugstore.inventory.dto.requestdto.ReduceStockRequestDTO;
import com.ssnhealthcare.drugstore.inventory.dto.responsedto.InventoryResponseDTO;
import com.ssnhealthcare.drugstore.inventory.dto.responsedto.ReduceStockResponseDTO;
import com.ssnhealthcare.drugstore.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/add")
    public ResponseEntity<InventoryResponseDTO> addInventory(
            @Valid @RequestBody AddInventoryRequestDTO dto){

        InventoryResponseDTO response = inventoryService.addInventory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/reduce")
    public ResponseEntity<ReduceStockResponseDTO> reduceStock(
            @Valid @RequestBody ReduceStockRequestDTO dto) {

        ReduceStockResponseDTO response = inventoryService.reduceStock(dto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getDrug/{drugId}")
    public ResponseEntity<InventoryResponseDTO> getInventoryByDrug(@PathVariable Long drugId){
        InventoryResponseDTO response = inventoryService.getInventoryByDrug(drugId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/lowStock")
    public ResponseEntity<List<InventoryResponseDTO>> getLowStockInventories(){
        List<InventoryResponseDTO> response = inventoryService.getLowStockInventories();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/expiring")
    public ResponseEntity<List<InventoryResponseDTO>> getExpiringInventories(
            @RequestParam(defaultValue = "30") Integer days){
        List<InventoryResponseDTO> response = inventoryService.getExpiringInventories(days);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllInventory")
    public ResponseEntity<Page<InventoryResponseDTO>> getAllInventory(@Valid
                   @RequestBody InventoryPageRequestDTO dto){
        Page<InventoryResponseDTO> response = inventoryService.getAllInventory(dto);
        return ResponseEntity.ok(response);
    }

}
