package com.ssnhealthcare.drugstore.returns.dto.responsedto;

import com.ssnhealthcare.drugstore.common.enums.ReturnReason;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReturnResponseDTO {

    private Long returnId;

    private Long inventoryId;
    private Long drugId;
    private String drugName;

    private String batchNumber;
    private Integer returnedQuantity;
    private Integer remainingQuantity;

    private ReturnReason reason;
    private LocalDate returnDate;
}
