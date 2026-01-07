package com.ssnhealthcare.drugstore.purchase.dto.Request;

import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;
import com.ssnhealthcare.drugstore.distributor.entity.Distributor;
import com.ssnhealthcare.drugstore.purchase.entity.PurchaseItem;
import com.ssnhealthcare.drugstore.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPurchaseOrderRequestDTO {

    @NotBlank(message = "Purchase-order cannot be empty")
    @NumberFormat
    private Long purchaseOrderId;

    @NotBlank(message = "Distributor cannot be empty")
    @NumberFormat
    private Long distributorId;

    @NotBlank(message = "Distributor cannot be empty")
    private Long distributorName;

    @NotBlank (message = "User cannot be empty")
    private User createdBy;

    @NotBlank (message = "Date cannot be empty")
    @FutureOrPresent
    private LocalDateTime orderDateTime;

    @NotBlank (message = "Status cannot be empty")
    private PurchaseStatus status;

    @NotBlank (message = "Items cannot be empty")
    private List <PurchaseItem> items;


}
