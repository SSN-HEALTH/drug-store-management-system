package com.ssnhealthcare.drugstore.order.repository;

import com.ssnhealthcare.drugstore.order.entity.OrderItem;
import com.ssnhealthcare.drugstore.report.Dto.SalesReportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Page<OrderItem> findByOrder_ProcessedBy_UserId(Long userId, Pageable pageable);

    @Query("""
        SELECT new com.ssnhealthcare.drugstore.report.Dto.SalesReportDto(
            oi.drug.drugName,
            SUM(oi.quantity),
            SUM(oi.quantity * oi.price)
        )
        FROM OrderItem oi
        JOIN oi.order o
        WHERE o.status = 'COMPLETED'
        AND o.orderDate BETWEEN :fromDate AND :toDate
        GROUP BY oi.drug.drugName
        ORDER BY SUM(oi.quantity) DESC
    """)
    List<SalesReportDto> getSalesReport(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

}
