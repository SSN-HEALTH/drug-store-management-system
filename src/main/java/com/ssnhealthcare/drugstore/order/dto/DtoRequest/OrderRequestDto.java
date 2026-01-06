package com.ssnhealthcare.drugstore.order.dto.DtoRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {

    @NotNull
    private long processedByUserId;
    @NotNull
    private List<OrderItemRequestDto> items;




}
