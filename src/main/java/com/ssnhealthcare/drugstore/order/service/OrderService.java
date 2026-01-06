package com.ssnhealthcare.drugstore.order.service;

import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import com.ssnhealthcare.drugstore.order.dto.DtoRequest.OrderRequestDto;
import com.ssnhealthcare.drugstore.order.dto.DtoResponse.OrderResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface OrderService {

    // Create Order (POS / Prescription)
    OrderResponseDto createOrder(@Valid OrderRequestDto orderRequestDto);

    // Get Order by ID
    OrderResponseDto getOrderById(Long orderId);

    // Get All Orders (Dashboard)
    Page<OrderResponseDto> getAllOrders(int page,int size);

    // Get Orders by Status
    Page<OrderResponseDto> getOrderByStatus(OrderStatus status);

    // Update Order Status
    OrderResponseDto updateOrderByStatus(Long orderId, OrderStatus status);

    // Orders by Date Range (Reports)
    Page<OrderResponseDto> getOrdersByDateRange(LocalDate fromDate, LocalDate toDate);

    // Orders Processed by User
    Page<OrderResponseDto> getOrderByProcessedUser(Long userId);

    // Cancel Order
    OrderResponseDto cancelOrder(Long orderId);

}
