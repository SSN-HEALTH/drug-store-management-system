package com.ssnhealthcare.drugstore.drug.repository;

import com.ssnhealthcare.drugstore.drug.entity.Drug;
import com.ssnhealthcare.drugstore.report.Dto.StockReportDto;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drug, Long>
{

    @Lock(LockModeType.PESSIMISTIC_WRITE)//lock the database execute one transaction at a time
    @Query("SELECT d FROM Drug d WHERE d.drugId = :drugId")
    Optional<Drug> findByIdForUpdate(Long drugId);
    // DrugRepository.java
    @Query("""
    SELECT new com.ssnhealthcare.drugstore.report.Dto.StockReportDto(
        d.drugName,
        COALESCE(SUM(i.quantity), 0L),
        COALESCE(SUM(oi.quantity), 0L)
    )
    FROM Drug d
    LEFT JOIN Inventory i ON i.drug = d
    LEFT JOIN OrderItem oi ON oi.drug = d AND oi.order.status = com.ssnhealthcare.drugstore.common.enums.OrderStatus.COMPLETED
    GROUP BY d.drugName
    ORDER BY COALESCE(SUM(i.quantity), 0L) ASC
""")
    List<StockReportDto> getStockReport();

}
