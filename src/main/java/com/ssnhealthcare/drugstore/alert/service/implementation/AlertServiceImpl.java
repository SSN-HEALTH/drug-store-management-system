package com.ssnhealthcare.drugstore.alert.service.implementation;

import com.ssnhealthcare.drugstore.alert.dto.AlertResponseDTO;
import com.ssnhealthcare.drugstore.alert.entity.Alert;
import com.ssnhealthcare.drugstore.alert.repository.AlertRepository;
import com.ssnhealthcare.drugstore.alert.service.AlertService;
import com.ssnhealthcare.drugstore.common.enums.AlertType;
import com.ssnhealthcare.drugstore.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;

    @Override
    public boolean createLowStockAlert(Long drugId, String drugName, Integer quantity) {

        if (alertRepository.existsByReferenceIdAndAlertTypeAndResolvedFalse(
                drugId, AlertType.LOW_STOCK)) return false;

        saveAlert(
                drugId,
                AlertType.LOW_STOCK,
                "Low stock for drug: " + drugName + " | Available: " + quantity
        );
        return true;
    }

    @Override
    public boolean createNearExpiryAlert(Long drugId, String drugName, LocalDate expiryDate) {

        if (alertRepository.existsByReferenceIdAndAlertTypeAndResolvedFalse(
                drugId, AlertType.NEAR_EXPIRY)) return false;

        saveAlert(
                drugId,
                AlertType.NEAR_EXPIRY,
                "Drug nearing expiry: " + drugName + " | Expiry: " + expiryDate
        );
        return true;
    }

    @Override
    public boolean createExpiredAlert(Long drugId, String drugName, LocalDate expiryDate) {

        if (alertRepository.existsByReferenceIdAndAlertTypeAndResolvedFalse(
                drugId, AlertType.EXPIRED)) return false;

        saveAlert(
                drugId,
                AlertType.EXPIRED,
                "Drug expired: " + drugName + " | Expired on: " + expiryDate
        );
        return true;
    }

    @Override
    public boolean createPendingOrderAlert(Long orderId) {

        if (alertRepository.existsByReferenceIdAndAlertTypeAndResolvedFalse(
                orderId, AlertType.PENDING_ORDER)) return false;

        saveAlert(
                orderId,
                AlertType.PENDING_ORDER,
                "Order ID " + orderId + " is pending"
        );
        return true;
    }

    @Override
    public Page<AlertResponseDTO> getActiveAlerts(int page, int size) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by("createdAt").descending());

        return alertRepository.findByResolvedFalse(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public void resolveAlert(Long alertId) {

        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() ->
                        new BusinessException("Alert not found: " + alertId));

        alert.setResolved(true);
        alertRepository.save(alert);
    }

    private void saveAlert(Long refId, AlertType type, String message) {

        Alert alert = new Alert();
        alert.setReferenceId(refId);
        alert.setAlertType(type);
        alert.setMessage(message);

        alertRepository.save(alert);
    }

    private AlertResponseDTO mapToResponse(Alert alert) {

        AlertResponseDTO dto = new AlertResponseDTO();
        dto.setId(alert.getId());
        dto.setAlertType(alert.getAlertType());
        dto.setReferenceId(alert.getReferenceId());
        dto.setMessage(alert.getMessage());
        dto.setResolved(alert.isResolved());
        dto.setCreatedAt(alert.getCreatedAt());
        return dto;
    }
}
