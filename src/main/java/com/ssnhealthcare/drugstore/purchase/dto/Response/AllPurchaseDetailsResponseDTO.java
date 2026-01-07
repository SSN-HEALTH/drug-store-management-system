package com.ssnhealthcare.drugstore.purchase.dto.Response;

import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;
import com.ssnhealthcare.drugstore.purchase.entity.PurchaseItem;
import com.ssnhealthcare.drugstore.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllPurchaseDetailsResponseDTO {

    private Long purchaseOrderId;
    private Long distributorId;
    private User createdBy;
    private LocalDateTime orderDateTime;
    private PurchaseStatus status;
    private BigDecimal orderAmount;
    private List<PurchaseItem> items;
    private String distributorName;

}
