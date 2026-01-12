package com.ssnhealthcare.drugstore.alert.controller;

import com.ssnhealthcare.drugstore.alert.dto.AlertResponseDTO;
import com.ssnhealthcare.drugstore.alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/getAlerts")
    public ResponseEntity<Page<AlertResponseDTO>> getActiveAlerts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(alertService.getActiveAlerts(page, size));
    }

    @PutMapping("/resolve/{id}")
    public ResponseEntity<Void> resolveAlert(@PathVariable Long id) {
        alertService.resolveAlert(id);
        return ResponseEntity.noContent().build();
    }
}