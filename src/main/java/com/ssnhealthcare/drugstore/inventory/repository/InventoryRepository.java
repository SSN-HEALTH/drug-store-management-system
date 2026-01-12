package com.ssnhealthcare.drugstore.inventory.repository;

import com.ssnhealthcare.drugstore.drug.entity.Drug;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import com.ssnhealthcare.drugstore.report.Dto.StockReportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // ---------- BASIC ----------
    Optional<Inventory> findByDrug(Drug drug);

    Optional<Inventory> findByDrug_DrugId(Long drugId);

    boolean existsByDrug(Drug drug);

    List<Inventory> findByExpiryDateBefore(LocalDate date);

    // ---------- EXPIRY REPORT ----------
    @Query("""
        SELECT i FROM Inventory i
        WHERE i.expiryDate <= :thresholdDate
        AND i.quantity > 0
    """)
    Page<Inventory> findExpiringInventory(
            @Param("thresholdDate") LocalDate thresholdDate,
            Pageable pageable
    );

    // ---------- STOCK REPORT ----------
    @Query("""
SELECT new com.ssnhealthcare.drugstore.report.Dto.StockReportDto(
    d.drugName,
    i.batchNumber,
    i.quantity,
    COALESCE(SUM(CASE WHEN s.sale.status = 'COMPLETED' THEN s.quantity ELSE 0 END), 0)
)
FROM Inventory i
JOIN i.drug d
LEFT JOIN SaleItem s ON s.drug = d
GROUP BY i.inventoryId, d.drugName, i.batchNumber, i.quantity
""")
    List<StockReportDto> generateStockReport();

    // ---------- AVAILABLE STOCK BY DRUG ----------
    @Query("""
        SELECT COALESCE(SUM(i.quantity), 0)
        FROM Inventory i
        WHERE i.drug.drugName = :drugName
    """)
    Integer getAvailableStockByDrugName(@Param("drugName") String drugName);

    // LOW STOCK
    @Query("""
        SELECT i FROM Inventory i
        WHERE i.quantity <= i.reorderLevel
    """)
    List<Inventory> findLowStock();

    // NEAR EXPIRY + EXPIRED
    List<Inventory> findByExpiryDateBeforeAndQuantityGreaterThan(
            LocalDate date,
            Integer quantity
    );
}
