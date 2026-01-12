package com.ssnhealthcare.drugstore.order.repository;

import com.ssnhealthcare.drugstore.dashboard.dto.TopSellingDrugDto;
import com.ssnhealthcare.drugstore.order.entity.OrderItem;
import com.ssnhealthcare.drugstore.report.Dto.SalesReportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


    @Query("""
        SELECT new com.ssnhealthcare.drugstore.dashboard.dto.TopSellingDrugDto(
            d.drugName,
            SUM(oi.quantity)
        )
        FROM OrderItem oi
        JOIN oi.drug d
        JOIN oi.order o
        WHERE o.status = 'COMPLETED'
          AND o.orderDate BETWEEN :startDate AND :endDate
        GROUP BY d.drugName
        ORDER BY SUM(oi.quantity) DESC
    """)
    Page<TopSellingDrugDto> findTopSellingDrugs(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
