package com.ssnhealthcare.drugstore.sale.Dto.DtoResponse;

import com.ssnhealthcare.drugstore.sale.entity.SaleItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data

public class SaleItemResponseDto {
    private Long drugId;
    private String drugName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal lineTotal;

    public SaleItemResponseDto(SaleItem item) {
        this.drugId = item.getDrug().getDrugId();
        this.drugName = item.getDrug().getDrugName();
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
    }
}