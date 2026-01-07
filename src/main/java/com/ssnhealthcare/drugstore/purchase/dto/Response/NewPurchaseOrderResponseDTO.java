package com.ssnhealthcare.drugstore.purchase.dto.Response;

import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;
import com.ssnhealthcare.drugstore.distributor.entity.Distributor;
import com.ssnhealthcare.drugstore.purchase.entity.PurchaseItem;
import com.ssnhealthcare.drugstore.purchase.entity.PurchaseOrder;
import com.ssnhealthcare.drugstore.user.entity.User;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class NewPurchaseOrderResponseDTO {

    private Long purchaseOrderId;
    private Long distributorId;
    private User createdBy;
    private LocalDateTime orderDateTime;
    private PurchaseStatus status;
    private BigDecimal orderAmount;
    private List<PurchaseItem> items;
    private String distributorName;

    public static NewPurchaseOrderResponseDTO from(PurchaseOrder order) {

        if (order == null) {
            throw new IllegalArgumentException("PurchaseOrder cannot be null");
        }

        NewPurchaseOrderResponseDTO dto = new NewPurchaseOrderResponseDTO();

        dto.purchaseOrderId = order.getPurchaseOrderId();
        //   dto.invoiceNumber = order.getInvoiceNumber();
        dto.orderDateTime = order.getOrderDateTime();
        dto.status = order.getStatus();
        dto.orderAmount = order.getOrderAmount();


        if (order.getDistributor() != null) {
            dto.distributorId = order.getDistributor().getDistributorId();
            dto.distributorName = order.getDistributor().getDistributorName();
        }

        return dto;
    }
}
