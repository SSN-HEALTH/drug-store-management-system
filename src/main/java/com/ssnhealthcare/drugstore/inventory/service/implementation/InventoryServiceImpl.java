package com.ssnhealthcare.drugstore.inventory.service.implementation;

import com.ssnhealthcare.drugstore.common.mapper.InventoryMapper;
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
    private final InventoryMapper inventoryMapper;

    @Override
    @Transactional
    public InventoryResponseDTO addInventory(AddInventoryRequestDTO dto) {

        Drug drug = drugRepository.findById(dto.getId())
                .orElseThrow(() ->
                        new DrugNotFoundException("Drug not found with id: " + dto.getId()));

        Inventory inventory = new Inventory();
        inventory.setDrug(drug);
        inventory.setQuantity(dto.getQuantity());
        inventory.setReorderLevel(dto.getReorderLevel());
        inventory.setExpiryDate(dto.getExpiryDate());
        inventory.setBatchNumber(dto.getBatchNumber());

        return inventoryMapper.toResponse(
                inventoryRepository.save(inventory)
        );
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

        return inventoryMapper.toResponse(inventory);
    }

    @Override
    public List<InventoryResponseDTO> getLowStockInventories() {

        return inventoryRepository.findAll().stream()
                .filter(inv -> inv.getQuantity() <= inv.getReorderLevel())
                .map(inventoryMapper::toResponse)
                .toList();
    }

    @Override
    public List<InventoryResponseDTO> getExpiringInventories(Integer days) {

        LocalDate expiryLimitDate = LocalDate.now().plusDays(days);

        return inventoryRepository.findByExpiryDateBefore(expiryLimitDate).stream()
                .map(inventoryMapper::toResponse)
                .toList();
    }

    @Override
    public Page<InventoryResponseDTO> getAllInventory(InventoryPageRequestDTO dto) {

        Pageable pageable = PageRequest.of(
                dto.getPageNumber(),
                dto.getSize(),
                Sort.by("expiryDate").ascending()
        );

        return inventoryRepository.findAll(pageable)
                .map(inventoryMapper::toResponse);
    }
}
