package com.ssnhealthcare.drugstore.purchase.dto.Request;

import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;
import com.ssnhealthcare.drugstore.user.entity.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Data
public class NewPurchaseOrderRequestDTO {

    @NotNull(message = "Distributor Id cannot be null")
    private Long distributorId;

    @NotBlank(message = "Distributor name cannot be blank")
    private String distributorName;

    @NotNull(message = "CreatedBy user cannot be null")
    private Long createdBy;

    @NotNull(message = "Order date cannot be null")
    @PastOrPresent
    private LocalDateTime orderDateTime;

    @NotNull(message = "Status cannot be null")
    private PurchaseStatus status;

    @NotEmpty(message = "Items cannot be empty")
    private List<PurchaseItemRequestDTO> items;

}
