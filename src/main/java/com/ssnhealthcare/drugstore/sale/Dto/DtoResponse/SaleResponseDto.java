package com.ssnhealthcare.drugstore.sale.Dto.DtoResponse;

import com.ssnhealthcare.drugstore.sale.entity.Sale;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleResponseDto {

    private Long saleId;
    private LocalDateTime saleDate;
    private Double totalAmount;
    private String orderStatus;
    private String processedBy;
    private String paymentMode;
    private List<SaleItemResponseDto> items;

    public SaleResponseDto(Sale sale) {
        this.saleId = sale.getSaleId();
        this.saleDate = sale.getSaleDate();
        this.totalAmount = sale.getTotalAmount();
        this.orderStatus = sale.getStatus().name();
        this.processedBy = sale.getProcessedBy().getUsername();
        this.paymentMode = sale.getPaymentMode().name();     // if exists in entity
        this.items = sale.getItems()
                .stream()
                .map(SaleItemResponseDto::new)
                .toList();
    }
}
