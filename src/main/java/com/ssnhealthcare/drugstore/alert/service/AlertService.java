package com.ssnhealthcare.drugstore.alert.service;

import com.ssnhealthcare.drugstore.alert.dto.AlertResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface AlertService {

    void createLowStockAlert(Long drugId, String drugName, Integer quantity);

    void createNearExpiryAlert(Long drugId, String drugName, LocalDate expiryDate);

    void createExpiredAlert(Long drugId, String drugName, LocalDate expiryDate);

    void createPendingOrderAlert(Long orderId);

    Page<AlertResponseDTO> getActiveAlerts(int page, int size);

    void resolveAlert(Long alertId);
}
