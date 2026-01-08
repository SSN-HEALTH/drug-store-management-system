package com.ssnhealthcare.drugstore.inventory.repository;

import com.ssnhealthcare.drugstore.drug.entity.Drug;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import com.ssnhealthcare.drugstore.report.Dto.StockReportDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByDrug(Drug drug);

    boolean existsByDrug(Drug drug);

    List<Inventory> findByQuantityLessThanEqual(Integer reorderLevel);

    List<Inventory> findByExpiryDateBefore(LocalDate date);

    Optional<Inventory> findByDrug_DrugId(Long drugId);
    @Query("""
    SELECT new com.ssnhealthcare.drugstore.report.Dto.StockReportDto(
        d.drugName,
        i.batchNumber,
        (i.quantity - (
            SELECT COALESCE(SUM(si.quantity), 0)
            FROM SaleItem si
            JOIN si.sale s
            WHERE si.drug = d
              AND s.status = 'COMPLETED'
        )),
        (
            SELECT COALESCE(SUM(si.quantity), 0)
            FROM SaleItem si
            JOIN si.sale s
            WHERE si.drug = d
              AND s.status = 'COMPLETED'
        )
    )
    FROM Inventory i
    JOIN i.drug d
""")
    List<StockReportDto> getStockReport();

    Optional<Inventory> findByDrug_DrugName(String drugName);
}
