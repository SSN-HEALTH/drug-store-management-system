package com.ssnhealthcare.drugstore.purchase.controller;

import com.ssnhealthcare.drugstore.purchase.dto.Request.*;
import com.ssnhealthcare.drugstore.purchase.dto.Response.*;
import com.ssnhealthcare.drugstore.purchase.service.PurchaseOrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {

    private PurchaseOrderService purchaseOrderService;

    @GetMapping("/allPurchases")
    public Page<PurchaseResponseDTO>
    getAllPurchaseDetails(@Valid @RequestBody AllPurchaseDetailsRequestDTO dto) {

        Page <PurchaseResponseDTO> response =
                purchaseOrderService.getAllPurchaseDetails(dto);
        return ResponseEntity.ok(response).getBody();
    }

    @PostMapping("/createPurchase")
    public ResponseEntity<PurchaseResponseDTO> newPurchaseOrder(@Valid @RequestBody NewPurchaseOrderRequestDTO dto) {

        PurchaseResponseDTO response = purchaseOrderService.newPurchaseOrder(dto);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<PurchaseResponseDTO> getPurchaseById(@PathVariable Long id){
        PurchaseResponseDTO response = purchaseOrderService.purchaseOrderById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/purchase/{id}/cancel")
    public ResponseEntity <PurchaseResponseDTO> cancelPurchaseById(@PathVariable Long id) {
        PurchaseResponseDTO response = purchaseOrderService.cancelOrderById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/purchaseByDates")
    public ResponseEntity<Page<PurchaseResponseDTO>>
    getPurchasesBetweenDates(
            @Valid @RequestBody PurchaseBetweenDatesRequestDTO dto) {

        Page<PurchaseResponseDTO> response =
                purchaseOrderService.getPurchaseBetweenDates(dto);

        return ResponseEntity.ok(response);
    }

}
