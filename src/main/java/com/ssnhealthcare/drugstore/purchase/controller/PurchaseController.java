package com.ssnhealthcare.drugstore.purchase.controller;

import com.ssnhealthcare.drugstore.purchase.dto.Request.AllPurchaseDetailsRequestByDateDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Request.AllPurchaseDetailsRequestDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Request.NewPurchaseOrderRequestDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Request.PurchaseOrderCancelRequestDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Request.PurchaseOrderRequestDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Response.AllPurchaseDetailsResponseByDateDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Response.AllPurchaseDetailsResponseDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Response.NewPurchaseOrderResponseDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Response.PurchaseOrderCancelResponseDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Response.PurchaseOrderResponseDTO;
import com.ssnhealthcare.drugstore.purchase.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {

    private PurchaseOrderService purchaseOrderService;

    @GetMapping("/allPurchases")
    public Page<AllPurchaseDetailsResponseDTO>
    getAllPurchaseDetails(@Valid @RequestBody AllPurchaseDetailsRequestDTO Requestdto) {

        Page <AllPurchaseDetailsResponseDTO> response =
                purchaseOrderService.getAllPurchaseDetails(Requestdto);
        return ResponseEntity.ok(response).getBody();
    }

    @PostMapping("/createPurchase")
    public ResponseEntity<NewPurchaseOrderResponseDTO> newPurchaseOrder(@Valid @RequestBody NewPurchaseOrderRequestDTO dto) {

        NewPurchaseOrderResponseDTO response = purchaseOrderService.newPurchaseOrder(dto).getBody();
        return ResponseEntity.ok(response);

    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<PurchaseOrderResponseDTO> getPurchaseById(@Valid @RequestBody PurchaseOrderRequestDTO dto){
        PurchaseOrderResponseDTO response = purchaseOrderService.PurchaseOrder(dto).getBody();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/purchases/{id}/cancel")
    public ResponseEntity <PurchaseOrderCancelResponseDTO> cancelPurchaseById(@Valid @RequestBody PurchaseOrderCancelRequestDTO dto) {
        PurchaseOrderCancelResponseDTO response = purchaseOrderService
    }


}
