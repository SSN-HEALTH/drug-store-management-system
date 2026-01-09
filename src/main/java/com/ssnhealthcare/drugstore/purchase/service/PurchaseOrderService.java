package com.ssnhealthcare.drugstore.purchase.service;


import com.ssnhealthcare.drugstore.purchase.dto.Request.*;

import com.ssnhealthcare.drugstore.purchase.dto.Response.*;
import org.springframework.data.domain.Page;

import java.util.Optional;


public interface PurchaseOrderService {

    Page<PurchaseResponseDTO> getAllPurchaseDetails(AllPurchaseDetailsRequestDTO dto);

    PurchaseResponseDTO newPurchaseOrder(NewPurchaseOrderRequestDTO dto);

    PurchaseResponseDTO purchaseOrderById(Long id);

    PurchaseResponseDTO cancelOrderById(Long id);

    Page<PurchaseResponseDTO> getPurchaseBetweenDates(PurchaseBetweenDatesRequestDTO dto);

}
