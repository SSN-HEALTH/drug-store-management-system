package com.ssnhealthcare.drugstore.purchase.dto.Request;

import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;
import com.ssnhealthcare.drugstore.distributor.entity.Distributor;
import com.ssnhealthcare.drugstore.purchase.entity.PurchaseItem;
import com.ssnhealthcare.drugstore.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotNull(message = "Purchase-order cannot be NULL")
    @NumberFormat
    private Long purchaseOrderId;

    @NotBlank (message = "invoiceNumber cannot be blank")
    private String invoiceNumber;


    @NotNull(message = "distributor Id cannot be NULL")
    @NumberFormat
    private Long distributorId;

    @NotNull(message = "distributor Name cannot be NULL")
    private Long distributorName;

    @NotBlank (message = "User cannot be empty")
    private Long createdBy;

    @NotNull (message = "Date cannot be empty")
    @FutureOrPresent
    private LocalDateTime orderDateTime;

    @NotNull (message = "Status cannot be empty")
    private PurchaseStatus status;

    @NotNull (message = "Items cannot be empty")
    private List <PurchaseItem> items;


}
