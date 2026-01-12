package com.ssnhealthcare.drugstore.sale.repository;

import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import com.ssnhealthcare.drugstore.sale.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale,Long>
{
    Page<Sale> findByProcessedBy_UserId(Long userId, Pageable pageable);


    Page<Sale> findBySaleDateBetween(
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);

    @Query("""
        SELECT COALESCE(SUM(s.totalAmount), 0)
        FROM Sale s
        WHERE s.status = 'COMPLETED'
          AND s.saleDate BETWEEN :from AND :to
    """)
    BigDecimal calculateTotalSales(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") OrderStatus status
    );
    List<Sale> findByStatus(OrderStatus status);
}
