package com.ssnhealthcare.drugstore.dashboard.dto;

import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecentOrderDto
{
    private Long orderId;
    private OrderStatus status;
    private BigDecimal orderValue;
    private LocalDateTime orderDate;
}
