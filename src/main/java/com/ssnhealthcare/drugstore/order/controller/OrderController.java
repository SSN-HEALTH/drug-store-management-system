package com.ssnhealthcare.drugstore.order.controller;

import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import com.ssnhealthcare.drugstore.order.dto.DtoRequest.OrderRequestDto;
import com.ssnhealthcare.drugstore.order.dto.DtoResponse.OrderResponseDto;
import com.ssnhealthcare.drugstore.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // create orders
    @PostMapping("/CreateOrders")
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto)
    {

        OrderResponseDto response = orderService.createOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //getById
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId)
    {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    //get all orders (DashBoard)
    @GetMapping("/allOrders")
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.getAllOrders(page, size));
    }


    //getByStatus
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersByStatus(@PathVariable OrderStatus status)
    {
        return ResponseEntity.ok(orderService.getOrderByStatus(status));
    }

    //Update Order Status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderByStatus(@PathVariable Long orderId, @RequestParam OrderStatus status)
    {
        return ResponseEntity.ok(orderService.updateOrderByStatus(orderId, status));
    }

    // Orders by Date Range (Reports)
    @GetMapping("/date-range")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersByDateRange(@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate)
    {
        return ResponseEntity.ok(orderService.getOrdersByDateRange(fromDate, toDate));
    }

    //orders processes by order
    @GetMapping("/processed-by/{userId}")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersByProcessedUser(@PathVariable Long userId)
    {
        return ResponseEntity.ok(orderService.getOrderByProcessedUser(userId));
    }

    //cancel Orders
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable Long orderId)
    {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }
}
