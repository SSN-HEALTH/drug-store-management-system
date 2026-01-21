package com.ssnhealthcare.drugstore.alert.service;

import com.ssnhealthcare.drugstore.alert.dto.AlertResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface AlertService {

    boolean createLowStockAlert(Long drugId, String drugName, Integer quantity);

    boolean createNearExpiryAlert(Long drugId, String drugName, LocalDate expiryDate);

    boolean createExpiredAlert(Long drugId, String drugName, LocalDate expiryDate);

    boolean createPendingOrderAlert(Long orderId);

    Page<AlertResponseDTO> getActiveAlerts(int page, int size);

    void resolveAlert(Long alertId);
}
