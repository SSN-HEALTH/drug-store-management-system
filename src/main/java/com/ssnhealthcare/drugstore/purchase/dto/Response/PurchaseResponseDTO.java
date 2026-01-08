package com.ssnhealthcare.drugstore.purchase.dto.Response;

import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;
import com.ssnhealthcare.drugstore.purchase.entity.PurchaseItem;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class PurchaseResponseDTO {

    private Long purchaseOrderId;

    private String distributorName;

    private Long distributorId;

    private String invoiceNumber;

    private LocalDateTime orderDateTime;

    private PurchaseStatus status;

    private BigDecimal orderAmount;

    private String createdBy;

    private List<PurchaseItem> items;
}

