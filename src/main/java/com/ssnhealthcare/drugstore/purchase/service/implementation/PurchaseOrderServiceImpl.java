package com.ssnhealthcare.drugstore.purchase.service.implementation;

import com.ssnhealthcare.drugstore.common.enums.DistributorStatus;
import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;
import com.ssnhealthcare.drugstore.distributor.entity.Distributor;
import com.ssnhealthcare.drugstore.distributor.repository.DistributorRepository;
import com.ssnhealthcare.drugstore.exception.DistributorNotFoundException;
import com.ssnhealthcare.drugstore.exception.InvalidPurchaseStateException;
import com.ssnhealthcare.drugstore.exception.PurchaseNotFoundException;
import com.ssnhealthcare.drugstore.purchase.dto.Request.*;
import com.ssnhealthcare.drugstore.purchase.dto.Response.*;
import com.ssnhealthcare.drugstore.purchase.entity.PurchaseOrder;
import com.ssnhealthcare.drugstore.purchase.repository.PurchaseOrderRepository;
import com.ssnhealthcare.drugstore.purchase.service.PurchaseOrderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final DistributorRepository distributorRepository;

    @Override
    @Transactional
    public Page<PurchaseResponseDTO> getAllPurchaseDetails(AllPurchaseDetailsRequestDTO dto) {

        Pageable pageable = PageRequest.of(
                dto.getPageNumber(),
                dto.getSize(),
                Sort.by("orderDateTime").descending()
        );

        return purchaseOrderRepository
                .findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public PurchaseResponseDTO newPurchaseOrder(NewPurchaseOrderRequestDTO dto) {

        Distributor distributor = distributorRepository
                .findById(dto.getDistributorId())
                .orElseThrow(() ->
                        new DistributorNotFoundException("Distributor not found"));

        if (distributor.getStatus() != DistributorStatus.ACTIVE) {
            throw new InvalidPurchaseStateException(
                    "Purchase cannot be created for inactive distributor");
        }

        if (dto.getOrderDateTime().isAfter(LocalDateTime.now())) {
            throw new InvalidPurchaseStateException(
                    "Purchase date cannot be in the future");
        }

        PurchaseOrder purchase = new PurchaseOrder();
        purchase.setDistributorId(distributor);
        purchase.setOrderDateTime(dto.getOrderDateTime());
        purchase.setStatus(PurchaseStatus.CREATED);
        purchase.setOrderAmount(BigDecimal.ZERO);
        purchase.setInvoiceNumber(generateAccountNumber()); // invoice later

        return mapToResponse(purchaseOrderRepository.save(purchase));
    }


    private String generateAccountNumber() {
        return String.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits())).substring(0, 10);
    }

    @Override
    @Transactional
    public PurchaseResponseDTO purchaseOrderById(
            PurchaseOrderRequestDTO dto) {

        PurchaseOrder purchase = purchaseOrderRepository
                .findById(dto.getPurchaseOrderId())
                .orElseThrow(() ->
                        new PurchaseNotFoundException(
                                "Purchase does not exist with id " + dto.getPurchaseOrderId()));

        return mapToResponse(purchase);
    }

    @Override
    public PurchaseResponseDTO cancelOrderById(
            PurchaseOrderCancelRequestDTO dto) {

        PurchaseOrder purchase = purchaseOrderRepository
                .findById(dto.getPurchaseOrderId())
                .orElseThrow(() ->
                        new PurchaseNotFoundException(
                                "Purchase does not exist with id " + dto.getPurchaseOrderId()));

        if (purchase.getStatus() == PurchaseStatus.COMPLETED) {
            throw new InvalidPurchaseStateException(
                    "Completed purchase cannot be cancelled");
        }

        if (purchase.getStatus() == PurchaseStatus.CANCELLED) {
            throw new InvalidPurchaseStateException(
                    "Purchase is already cancelled");
        }

        purchase.setStatus(PurchaseStatus.CANCELLED);
        return mapToResponse(purchase);
    }

    @Override
    @Transactional
    public Page<PurchaseResponseDTO> getPurchaseBetweenDates(
            PurchaseBetweenDatesRequestDTO dto) {

        validateDateRange(dto);

        Pageable pageable = PageRequest.of(
                dto.getPageNumber(),
                dto.getSize(),
                Sort.by("orderDateTime").descending()
        );

        return purchaseOrderRepository
                .findByOrderDateTimeBetween(
                        dto.getFromDate().atStartOfDay(),
                        dto.getToDate().atTime(23, 59, 59),
                        pageable
                )
                .map(this::mapToResponse);
    }

    private void validateDateRange(PurchaseBetweenDatesRequestDTO dto) {

        if (dto.getFromDate().isAfter(dto.getToDate())) {
            throw new InvalidPurchaseStateException(
                    "From date cannot be after To date");
        }

        if (dto.getToDate().isAfter(LocalDateTime.now().toLocalDate())) {
            throw new InvalidPurchaseStateException(
                    "To date cannot be in the future");
        }
    }

    private PurchaseResponseDTO mapToResponse(PurchaseOrder p) {

        PurchaseResponseDTO dto = new PurchaseResponseDTO();

        dto.setPurchaseOrderId(p.getPurchaseOrderId());
        dto.setInvoiceNumber(p.getInvoiceNumber());
        dto.setOrderDateTime(p.getOrderDateTime());
        dto.setStatus(p.getStatus());
        dto.setOrderAmount(p.getOrderAmount());
        dto.setCreatedBy(String.valueOf(p.getCreatedBy()));
        dto.setItems(p.getItems());

        if (p.getDistributorId() != null) {
            dto.setDistributorId(p.getDistributorId().getDistributorId());
            dto.setDistributorName(p.getDistributorId().getDistributorName());
        }

        return dto;
    }
}







