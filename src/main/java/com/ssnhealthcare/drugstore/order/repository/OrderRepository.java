package com.ssnhealthcare.drugstore.order.repository;

import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import com.ssnhealthcare.drugstore.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Page<Order> findByOrderDateBetween(
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Pageable pageable
    );


    Page<Order> findByProcessedBy_UserId(Long userId, Pageable pageable);


    @Query("""
        SELECT COUNT(s)
        FROM Sale s
        WHERE s.status = 'COMPLETED'
          AND s.saleDate BETWEEN :from AND :to
    """)
    Long countCompletedOrders(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);
}
