package com.ssnhealthcare.drugstore.order.repository;

import com.ssnhealthcare.drugstore.order.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Page<OrderItem> findByOrder_ProcessedBy_UserId(Long userId, Pageable pageable);
}
