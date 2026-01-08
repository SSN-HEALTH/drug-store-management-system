package com.ssnhealthcare.drugstore.alert.entity;

import com.ssnhealthcare.drugstore.common.enums.AlertType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "drug_id", nullable = false, updatable = false)
    private Long drugId;

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_type", nullable = false, length = 30)
    private AlertType alertType;

    @Column(name = "message", nullable = false, length = 200)
    private String message;

    @Column(name = "is_resolved", nullable = false)
    private boolean resolved = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

