package com.ssnhealthcare.drugstore.purchase.service.implementation;

import com.ssnhealthcare.drugstore.common.enums.DistributorStatus;
import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;
import com.ssnhealthcare.drugstore.distributor.entity.Distributor;
import com.ssnhealthcare.drugstore.distributor.repository.DistributorRepository;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import com.ssnhealthcare.drugstore.purchase.dto.Request.AllPurchaseDetailsRequestDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Request.PurchaseOrderCancelRequestDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Request.PurchaseOrderRequestDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Response.AllPurchaseDetailsResponseDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Response.PurchaseOrderCancelResponseDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Response.PurchaseOrderResponseDTO;
import com.ssnhealthcare.drugstore.purchase.exception.DistributorNotFoundException;
import com.ssnhealthcare.drugstore.purchase.exception.InvalidPurchaseStateException;
import com.ssnhealthcare.drugstore.purchase.dto.Request.AllPurchaseDetailsRequestByDateDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Request.NewPurchaseOrderRequestDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Response.AllPurchaseDetailsResponseByDateDTO;
import com.ssnhealthcare.drugstore.purchase.dto.Response.NewPurchaseOrderResponseDTO;
import com.ssnhealthcare.drugstore.purchase.entity.PurchaseOrder;
import com.ssnhealthcare.drugstore.purchase.exception.PurchaseNotFoundException;
import com.ssnhealthcare.drugstore.purchase.repository.PurchaseOrderRepository;
import com.ssnhealthcare.drugstore.purchase.service.PurchaseOrderService;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service

public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private PurchaseOrderRepository purchaseOrderRepository;
    private DistributorRepository distributorRepository;

    @Override
    public Page<AllPurchaseDetailsResponseDTO> getAllPurchaseDetails(AllPurchaseDetailsRequestDTO dto) {

        Pageable pageable = PageRequest.of(dto.getPageNumber(),dto.getSize(), Sort.by("orderDateTime").descending());

        Page<PurchaseOrder> response = purchaseOrderRepository.findAll(pageable);

        return response.map(this::mapToResponseDTO);
    }

    public AllPurchaseDetailsResponseDTO mapToResponseDTO(PurchaseOrder purchase){

        AllPurchaseDetailsResponseDTO response = new AllPurchaseDetailsResponseDTO();
        response.setPurchaseOrderId(purchase.getPurchaseOrderId());
        response.setDistributorId(purchase.getDistributor().getDistributorId());
       // response.setInvoiceNumber(purchase.getInvoiceNumber());
        response.setCreatedBy(purchase.getCreatedBy());
        response.setOrderDateTime(purchase.getOrderDateTime());
        response.setStatus(purchase.getStatus());
        response.setOrderAmount(purchase.getOrderAmount());
        response.setItems(purchase.getItems());
        return response;
    }





    @Override
    @Transactional
    public ResponseEntity<NewPurchaseOrderResponseDTO> newPurchaseOrder(NewPurchaseOrderRequestDTO dto) {


            /* ---------------- VALIDATE DISTRIBUTOR ---------------- */

            Distributor distributor = distributorRepository
                    .findById(dto.getDistributorId())
                    .orElseThrow(() ->
                            new DistributorNotFoundException(
                                    "Distributor not found"));

            if (distributor.getStatus() != DistributorStatus.ACTIVE) {
                throw new InvalidPurchaseStateException(
                        "Purchase cannot be created for inactive distributor");
            }

            /* ---------------- VALIDATE INVOICE ---------------- */

//            boolean invoiceExists =
//                    purchaseOrderRepository
//                            .existsByInvoiceNumber(request.getInvoiceNumber());
//
//            if (invoiceExists) {
//                throw new InvalidPurchaseStateException(
//                        "Invoice number already exists");
//            }

            /* ---------------- VALIDATE PURCHASE DATE ---------------- */

            if (dto.getOrderDateTime().isAfter(LocalDateTime.now())) {
                throw new InvalidPurchaseStateException(
                        "Purchase date cannot be in the future");
            }

            /* ---------------- CREATE PURCHASE ORDER ---------------- */

            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setDistributor(distributor);
      //      purchaseOrder.setInvoiceNumber(dto.getInvoiceNumber());
            purchaseOrder.setOrderDateTime(dto.getOrderDateTime());
            purchaseOrder.setStatus(PurchaseStatus.CREATED);
            purchaseOrder.setOrderAmount(BigDecimal.ZERO);

            PurchaseOrder savedOrder =
                    purchaseOrderRepository.save(purchaseOrder);

            /* ---------------- MAP RESPONSE ---------------- */

            return ResponseEntity.ok(NewPurchaseOrderResponseDTO.from(savedOrder));
        }

    @Override
    public ResponseEntity<PurchaseOrderResponseDTO> PurchaseOrder(PurchaseOrderRequestDTO dto) {

        PurchaseOrder purchase = purchaseOrderRepository.findById(dto.getPurchaseOrderId())
                .orElseThrow(() -> new PurchaseNotFoundException("Purchase does not exist with id " + dto.getPurchaseOrderId()));

        PurchaseOrderResponseDTO purchaseDTO = new PurchaseOrderResponseDTO();

        purchaseDTO.setPurchaseOrderId(purchase.getPurchaseOrderId());
        purchaseDTO.setDistributor(purchase.getDistributor());
        purchaseDTO.setInvoiceNumber(purchase.getInvoiceNumber());
        purchaseDTO.setCreatedBy(purchase.getCreatedBy());
        purchaseDTO.setOrderDateTime(purchase.getOrderDateTime());
        purchaseDTO.setStatus(purchase.getStatus());
        purchaseDTO.setOrderAmount(purchase.getOrderAmount());
        purchaseDTO.setItems(purchase.getItems());

        return ResponseEntity.ok(purchaseDTO);
    }

    @Override
    public ResponseEntity<PurchaseOrderCancelResponseDTO> PurchaseOrder(PurchaseOrderCancelRequestDTO dto) {

        PurchaseOrder purchase = purchaseOrderRepository.findById(dto.getPurchaseOrderId())
                .orElseThrow(() -> new PurchaseNotFoundException("Purchase does not exist with id " + dto.getPurchaseOrderId()));
        if (purchase.getStatus() == PurchaseStatus.COMPLETED) {
            throw new InvalidPurchaseStateException(
                    "Completed purchase cannot be cancelled");
        }

        if (purchase.getStatus() == PurchaseStatus.CANCELLED) {
            throw new InvalidPurchaseStateException(
                    "Purchase is already cancelled");
        }
            purchase.setStatus(PurchaseStatus.CANCELLED);
        PurchaseOrderCancelResponseDTO purchaseDTO = new PurchaseOrderCancelResponseDTO();
        purchaseDTO.setStatus(purchase.getStatus());
        return ResponseEntity.ok(purchaseDTO);
    }
}



