package com.ssnhealthcare.drugstore.inventory.service.implementation;

import com.ssnhealthcare.drugstore.drug.entity.Drug;
import com.ssnhealthcare.drugstore.drug.repository.DrugRepository;
import com.ssnhealthcare.drugstore.exception.BusinessException;
import com.ssnhealthcare.drugstore.exception.DrugNotFoundException;
import com.ssnhealthcare.drugstore.inventory.dto.requestdto.AddInventoryRequestDTO;
import com.ssnhealthcare.drugstore.inventory.dto.requestdto.InventoryPageRequestDTO;
import com.ssnhealthcare.drugstore.inventory.dto.requestdto.ReduceStockRequestDTO;
import com.ssnhealthcare.drugstore.inventory.dto.responsedto.InventoryResponseDTO;
import com.ssnhealthcare.drugstore.inventory.dto.responsedto.ReduceStockResponseDTO;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import com.ssnhealthcare.drugstore.inventory.repository.InventoryRepository;
import com.ssnhealthcare.drugstore.inventory.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final DrugRepository drugRepository;

    @Override
    @Transactional
    public InventoryResponseDTO addInventory(AddInventoryRequestDTO dto) {

        Drug drug = drugRepository.findById(dto.getId())
                .orElseThrow(() ->
                 new DrugNotFoundException("Drug not found with id: "
                 + dto.getId()));

        Inventory inventory = new Inventory();
        inventory.setDrug(drug);
        inventory.setQuantity(dto.getQuantity());
        inventory.setReorderLevel(dto.getReorderLevel());
        inventory.setExpiryDate(dto.getExpiryDate());
        inventory.setBatchNumber(dto.getBatchNumber());

        Inventory saveInventory = inventoryRepository.save(inventory);

        return mapToResponseDTO(saveInventory);
    }

    @Override
    @Transactional
    public ReduceStockResponseDTO reduceStock(ReduceStockRequestDTO dto) {

        Drug drug = drugRepository.findById(dto.getDrugId())
                .orElseThrow(() ->
                        new DrugNotFoundException("Drug not found with id: " + dto.getDrugId()));

        Inventory inventory = inventoryRepository.findByDrug(drug)
                .orElseThrow(() ->
                        new BusinessException("Inventory not found for drug id: " + dto.getDrugId()));

        int oldQuantity = inventory.getQuantity();

        if (oldQuantity < dto.getQuantity()) {
            throw new BusinessException("Insufficient stock available");
        }

        int remainingQuantity = oldQuantity - dto.getQuantity();
        inventory.setQuantity(remainingQuantity);

        inventoryRepository.save(inventory);


        ReduceStockResponseDTO response = new ReduceStockResponseDTO();
        response.setDrugId(drug.getDrugId());
        response.setDrugName(drug.getDrugName());
        response.setBatchNumber(inventory.getBatchNumber());
        response.setOldQuantity(oldQuantity);
        response.setReducedQuantity(dto.getQuantity());
        response.setRemainingQuantity(remainingQuantity);

        return response;
    }

    @Override
    public InventoryResponseDTO getInventoryByDrug(Long drugId) {

        Drug drug = drugRepository.findById(drugId)
            .orElseThrow(() ->
            new DrugNotFoundException("Drug not found with id: " + drugId));

        Inventory inventory = inventoryRepository.findByDrug(drug)
            .orElseThrow(() ->
            new BusinessException("Inventory not found for drug id: " + drugId));

        return mapToResponseDTO(inventory);
    }

    @Override
    public List<InventoryResponseDTO> getLowStockInventories() {

        List<Inventory> inventories = inventoryRepository.findAll();

        return inventories.stream()
                .filter(inv -> inv.getQuantity() <= inv.getReorderLevel())
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public List<InventoryResponseDTO> getExpiringInventories(Integer days) {
        LocalDate expiryLimitDate = LocalDate.now().plusDays(days);

        List<Inventory> inventories =
                inventoryRepository.findByExpiryDateBefore(expiryLimitDate);

        return inventories.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public Page<InventoryResponseDTO> getAllInventory(InventoryPageRequestDTO dto) {

        Pageable pageable = PageRequest.of(
                dto.getPageNumber(),
                dto.getSize(),
                Sort.by("expiryDate").ascending()
        );
        Page<Inventory> inventoryPage =
                inventoryRepository.findAll(pageable);

        return inventoryPage.map(this::mapToResponseDTO);
    }

    public InventoryResponseDTO mapToResponseDTO(Inventory inventory){

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
