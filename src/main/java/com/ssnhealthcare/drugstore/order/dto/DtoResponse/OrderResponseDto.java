package com.ssnhealthcare.drugstore.order.dto.DtoResponse;

import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import com.ssnhealthcare.drugstore.order.dto.DtoRequest.OrderItemRequestDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private String processedBy;
    private List<OrderItemResponseDto> items;
}
