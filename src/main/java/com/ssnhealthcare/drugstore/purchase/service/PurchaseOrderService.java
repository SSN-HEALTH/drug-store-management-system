package com.ssnhealthcare.drugstore.purchase.service;

import com.ssnhealthcare.drugstore.inventory.dto.requestdto.InventoryPageRequestDTO;
import com.ssnhealthcare.drugstore.inventory.dto.responsedto.InventoryResponseDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Request.AllPurchaseDetailsRequestDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Request.NewPurchaseOrderRequestDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Request.PurchaseOrderCancelRequestDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Request.PurchaseOrderRequestDTO;

import com.ssnhealthcare.drugstore.purchase.dto.Response.AllPurchaseDetailsResponseDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Response.NewPurchaseOrderResponseDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Response.PurchaseOrderCancelResponseDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Response.PurchaseOrderResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface PurchaseOrderService {

    Page<AllPurchaseDetailsResponseDTO> getAllPurchaseDetails(AllPurchaseDetailsRequestDTO dto);
    ResponseEntity <NewPurchaseOrderResponseDTO> newPurchaseOrder(NewPurchaseOrderRequestDTO dto);
    ResponseEntity <PurchaseOrderResponseDTO> PurchaseOrder(PurchaseOrderRequestDTO dto);
    ResponseEntity <PurchaseOrderCancelResponseDTO> PurchaseOrder(PurchaseOrderCancelRequestDTO dto);
 //   Page<InventoryResponseDTO> getAllInventory(@Valid InventoryPageRequestDTO dto);


}
