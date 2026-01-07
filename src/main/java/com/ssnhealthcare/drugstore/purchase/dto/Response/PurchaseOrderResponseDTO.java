package com.ssnhealthcare.drugstore.purchase.dto.Response;

import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;
import com.ssnhealthcare.drugstore.distributor.entity.Distributor;
import com.ssnhealthcare.drugstore.purchase.entity.PurchaseItem;
import com.ssnhealthcare.drugstore.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderResponseDTO {

    private Long purchaseOrderId;


    private Distributor distributor;


    private Long invoiceNumber;


    private User createdBy;


    private LocalDateTime orderDateTime;


    private PurchaseStatus status;


    private BigDecimal orderAmount;


    private List<PurchaseItem> items;


    }

