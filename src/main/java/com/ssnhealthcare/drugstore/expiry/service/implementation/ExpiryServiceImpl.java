package com.ssnhealthcare.drugstore.expiry.service.implementation;

import com.ssnhealthcare.drugstore.common.enums.ReturnReason;
import com.ssnhealthcare.drugstore.common.mapper.InventoryMapper;
import com.ssnhealthcare.drugstore.common.mapper.ReturnMapper;
import com.ssnhealthcare.drugstore.exception.BusinessException;
import com.ssnhealthcare.drugstore.expiry.dto.MarkExpiredRequestDTO;
import com.ssnhealthcare.drugstore.expiry.service.ExpiryService;
import com.ssnhealthcare.drugstore.inventory.dto.responsedto.InventoryResponseDTO;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import com.ssnhealthcare.drugstore.inventory.repository.InventoryRepository;
import com.ssnhealthcare.drugstore.returns.dto.responsedto.ReturnResponseDTO;
import com.ssnhealthcare.drugstore.returns.entity.Return;
import com.ssnhealthcare.drugstore.returns.repository.ReturnRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class ExpiryServiceImpl implements ExpiryService {

    private final InventoryRepository inventoryRepository;
    private final ReturnRepository returnRepository;
    private final InventoryMapper inventoryMapper;
    private final ReturnMapper returnMapper;

    @Override
    @Transactional
    public ReturnResponseDTO markAsExpired(MarkExpiredRequestDTO dto) {

        Inventory inventory = inventoryRepository.findById(dto.getInventoryId())
                .orElseThrow(() -> new BusinessException("Inventory not found"));

        inventory.setQuantity(inventory.getQuantity() - dto.getQuantity());

        Return ret = new Return();
        ret.setInventory(inventory);
        ret.setQuantity(dto.getQuantity());
        ret.setReason(ReturnReason.EXPIRED);
        ret.setReturnDate(LocalDate.now());

        return returnMapper.toResponse(returnRepository.save(ret));
    }

    @Override
    public Page<InventoryResponseDTO> getExpiringInventory(int days, int page, int size) {

        LocalDate thresholdDate = LocalDate.now().plusDays(days);
        Pageable pageable = PageRequest.of(page, size);

        return inventoryRepository
                .findExpiringInventory(thresholdDate, pageable)
                .map(inventoryMapper::toResponse);
    }
}

