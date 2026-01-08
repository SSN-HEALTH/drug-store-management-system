package com.ssnhealthcare.drugstore.expiry.service;

import com.ssnhealthcare.drugstore.expiry.dto.MarkExpiredRequestDTO;
import com.ssnhealthcare.drugstore.inventory.dto.responsedto.InventoryResponseDTO;
import com.ssnhealthcare.drugstore.returns.dto.responsedto.ReturnResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface ExpiryService {
    ReturnResponseDTO markAsExpired(@Valid MarkExpiredRequestDTO dto);

    Page<InventoryResponseDTO> getExpiringInventory(int days, int page, int size);
}
