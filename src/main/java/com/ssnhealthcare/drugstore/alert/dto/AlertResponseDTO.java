package com.ssnhealthcare.drugstore.alert.dto;

import com.ssnhealthcare.drugstore.common.enums.AlertType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertResponseDTO {

    private Long id;
    private AlertType alertType;
    private Long referenceId; // drugId / orderId
    private String message;
    private boolean resolved;
    private LocalDateTime createdAt;
}
