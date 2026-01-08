package com.ssnhealthcare.drugstore.expiry.controller;

import com.ssnhealthcare.drugstore.expiry.dto.MarkExpiredRequestDTO;
import com.ssnhealthcare.drugstore.expiry.service.ExpiryService;
import com.ssnhealthcare.drugstore.inventory.dto.responsedto.InventoryResponseDTO;
import com.ssnhealthcare.drugstore.returns.dto.responsedto.ReturnResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ExpiryController  {

    private final ExpiryService expiryService;

    @PostMapping("/mark-expired")
    public ResponseEntity<ReturnResponseDTO> markExpired(
            @Valid @RequestBody MarkExpiredRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(expiryService.markAsExpired(dto));
    }

    @GetMapping("/expiring")
    public ResponseEntity<Page<InventoryResponseDTO>> getExpiringInventory(
            @RequestParam(defaultValue = "30") int days,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                expiryService.getExpiringInventory(days, page, size)
        );
    }
}
