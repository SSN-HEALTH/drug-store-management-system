package com.ssnhealthcare.drugstore.purchase.dto.Response;

import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderCancelResponseDTO {

    private PurchaseStatus status;
}
