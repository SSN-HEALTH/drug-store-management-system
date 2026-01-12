package com.ssnhealthcare.drugstore.sale.repository;

import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import com.ssnhealthcare.drugstore.report.Dto.SalesReportDto;
import com.ssnhealthcare.drugstore.sale.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem,Long>
{




    @Query("""
        SELECT new com.ssnhealthcare.drugstore.report.Dto.SalesReportDto(
            d.drugName,
            COALESCE(SUM(si.quantity), 0),
            COALESCE(SUM(si.price * si.quantity), 0)
        )
        FROM SaleItem si
        JOIN si.drug d
        JOIN si.sale s
        WHERE s.status = 'COMPLETED'
          AND s.saleDate BETWEEN :fromDate AND :toDate
        GROUP BY d.drugName
        ORDER BY d.drugName
    """)
    List<SalesReportDto> getSalesReport(@Param("fromDate") LocalDateTime fromDate,
                                        @Param("toDate") LocalDateTime toDate);
    // TOTAL ITEMS SOLD
    @Query("""
        SELECT COALESCE(SUM(si.quantity), 0)
        FROM SaleItem si
        JOIN si.sale s
        WHERE s.status = :status
        AND s.saleDate BETWEEN :fromDate AND :toDate
    """)
    Long sumQuantityByOrderStatusAndDate(
            @Param("status") OrderStatus status,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    @Query("""
    SELECT COALESCE(SUM(si.quantity * si.price), 0)
    FROM SaleItem si
    JOIN si.sale s
    WHERE s.status = :status
    AND s.saleDate BETWEEN :fromDate AND :toDate
""")
    BigDecimal sumRevenueByOrderStatusAndDate(
            OrderStatus status,
            LocalDateTime fromDate,
            LocalDateTime toDate
    );
}
