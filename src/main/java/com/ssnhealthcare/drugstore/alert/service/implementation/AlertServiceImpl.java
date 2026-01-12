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
    public void createLowStockAlert(Long drugId, String drugName, Integer quantity) {

        if (alertRepository.existsByReferenceIdAndAlertTypeAndResolvedFalse(
                drugId, AlertType.LOW_STOCK)) return;

        saveAlert(
                drugId,
                AlertType.LOW_STOCK,
                "Low stock for drug: " + drugName + " | Available: " + quantity
        );
    }

    @Override
    public void createNearExpiryAlert(Long drugId, String drugName, LocalDate expiryDate) {

        if (alertRepository.existsByReferenceIdAndAlertTypeAndResolvedFalse(
                drugId, AlertType.NEAR_EXPIRY)) return;

        saveAlert(
                drugId,
                AlertType.NEAR_EXPIRY,
                "Drug nearing expiry: " + drugName + " | Expiry: " + expiryDate
        );
    }

    @Override
    public void createExpiredAlert(Long drugId, String drugName, LocalDate expiryDate) {

        if (alertRepository.existsByReferenceIdAndAlertTypeAndResolvedFalse(
                drugId, AlertType.EXPIRED)) return;

        saveAlert(
                drugId,
                AlertType.EXPIRED,
                "Drug expired: " + drugName + " | Expired on: " + expiryDate
        );
    }

    @Override
    public void createPendingOrderAlert(Long orderId) {

        if (alertRepository.existsByReferenceIdAndAlertTypeAndResolvedFalse(
                orderId, AlertType.PENDING_ORDER)) return;

        saveAlert(
                orderId,
                AlertType.PENDING_ORDER,
                "Order ID " + orderId + " is pending"
        );
    }

    @Override
    public Page<AlertResponseDTO> getActiveAlerts(int page, int size) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by("createdAt").descending());

        return alertRepository.findByResolvedFalse(pageable)
                .map(this::mapToDTO);
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

    private AlertResponseDTO mapToDTO(Alert alert) {

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
