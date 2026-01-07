package com.ssnhealthcare.drugstore.order.service.implementation;

import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import com.ssnhealthcare.drugstore.exception.BusinessException;
import com.ssnhealthcare.drugstore.exception.ResourceNotFoundException;
import com.ssnhealthcare.drugstore.drug.entity.Drug;
import com.ssnhealthcare.drugstore.drug.repository.DrugRepository;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import com.ssnhealthcare.drugstore.inventory.repository.InventoryRepository;
import com.ssnhealthcare.drugstore.order.dto.DtoRequest.OrderRequestDto;
import com.ssnhealthcare.drugstore.order.dto.DtoResponse.OrderItemResponseDto;
import com.ssnhealthcare.drugstore.order.dto.DtoResponse.OrderResponseDto;
import com.ssnhealthcare.drugstore.order.entity.Order;
import com.ssnhealthcare.drugstore.order.entity.OrderItem;
import com.ssnhealthcare.drugstore.order.repository.OrderRepository;
import com.ssnhealthcare.drugstore.order.service.OrderService;
import com.ssnhealthcare.drugstore.user.entity.User;
import com.ssnhealthcare.drugstore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DrugRepository drugRepository;
    private final InventoryRepository inventoryRepository;

    private static final int PAGE_SIZE = 10;

    // Create Order (POS / Prescription)
    @Override
    public OrderResponseDto createOrder(OrderRequestDto dto) {

        User user = userRepository.findById(dto.getProcessedByUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        order.setProcessedBy(user);

        // Map request items to OrderItem with inventory check
        List<OrderItem> items = dto.getItems().stream().map(i -> {
            Drug drug = drugRepository.findById(i.getDrugId())
                    .orElseThrow(() -> new ResourceNotFoundException("Drug not found"));

            // Fetch inventory
            Inventory inventory = inventoryRepository.findByDrug_DrugId(drug.getDrugId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for drug: " + drug.getDrugName()));

            // Expiry validation
            if (inventory.getExpiryDate().isBefore(LocalDate.now())) {
                throw new BusinessException("Drug expired: " + drug.getDrugName());
            }

            // Stock validation
            if (inventory.getQuantity() < i.getQuantity()) {
                throw new BusinessException("Insufficient stock for drug: " + drug.getDrugName());
            }

            // Deduct stock
            inventory.setQuantity(inventory.getQuantity() - i.getQuantity());
            inventoryRepository.save(inventory);

            // Create OrderItem
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setDrug(drug);
            item.setQuantity(i.getQuantity());
            item.setPrice(i.getPrice());
            return item;
        }).toList();

        order.setItems(items);

        // Calculate total
        BigDecimal total = items.stream()
                .map(it -> it.getPrice().multiply(BigDecimal.valueOf(it.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);

        return mapToResponse(orderRepository.save(order));
    }

    // Get Order by ID
    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        return mapToResponse(orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found")));
    }

    // Get All Orders (Dashboard)
    @Override
    public Page<OrderResponseDto> getAllOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size)).map(this::mapToResponse);
    }

    // Get Orders by Status
    @Override
    public Page<OrderResponseDto> getOrderByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status, PageRequest.of(0, PAGE_SIZE))
                .map(this::mapToResponse);
    }

    // Update Order Status
    @Override
    public OrderResponseDto updateOrderByStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new BusinessException("Cancelled order cannot be updated");
        }

        order.setStatus(status);
        return mapToResponse(orderRepository.save(order));
    }

    // Orders by Date Range (Reports)
    @Override
    public Page<OrderResponseDto> getOrdersByDateRange(LocalDate fromDate, LocalDate toDate) {
        return orderRepository.findByOrderDateBetween(
                        fromDate.atStartOfDay(),
                        toDate.atTime(23, 59, 59),
                        PageRequest.of(0, PAGE_SIZE))
                .map(this::mapToResponse);
    }

    // Orders Processed by User
    @Override
    public Page<OrderResponseDto> getOrderByProcessedUser(Long userId) {
        return orderRepository.findByProcessedBy_UserId(userId, PageRequest.of(0, PAGE_SIZE))
                .map(this::mapToResponse);
    }

    // Cancel Order
    @Override
    public OrderResponseDto cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new BusinessException("Completed order cannot be cancelled");
        }

        // Restore inventory if needed
        order.getItems().forEach(item -> {
            Inventory inventory = inventoryRepository.findByDrug_DrugId(item.getDrug().getDrugId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for drug: " + item.getDrug().getDrugName()));
            inventory.setQuantity(inventory.getQuantity() + item.getQuantity());
            inventoryRepository.save(inventory);
        });

        order.setStatus(OrderStatus.CANCELLED);

        return mapToResponse(orderRepository.save(order));
    }

    // Map Order entity to Response DTO
    private OrderResponseDto mapToResponse(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(order.getOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setProcessedBy(order.getProcessedBy().getUsername());

        List<OrderItemResponseDto> items = order.getItems().stream().map(i -> {
            OrderItemResponseDto itemDto = new OrderItemResponseDto();
            itemDto.setDrugId(i.getDrug().getDrugId());
            itemDto.setDrugName(i.getDrug().getDrugName());
            itemDto.setQuantity(i.getQuantity());
            itemDto.setPrice(i.getPrice());
            return itemDto;
        }).toList();

        dto.setItems(items);
        return dto;
    }
}
