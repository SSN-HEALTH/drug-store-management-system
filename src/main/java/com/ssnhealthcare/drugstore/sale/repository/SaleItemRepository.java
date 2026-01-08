package com.ssnhealthcare.drugstore.sale.repository;

import com.ssnhealthcare.drugstore.report.Dto.SalesReportDto;
import com.ssnhealthcare.drugstore.sale.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem,Long>
{
    // Fetch all items for a sale (Invoice view)
    List<SaleItem> findBySale_SaleId(Long saleId);

    // Fetch sale items by drug (Stock movement / analytics)
    List<SaleItem> findByDrug_DrugId(Long drugId);



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
    @Query("""
        SELECT COALESCE(SUM(si.quantity), 0)
        FROM SaleItem si
        JOIN si.sale s
        WHERE s.status = 'COMPLETED'
          AND s.saleDate BETWEEN :from AND :to
    """)
    Long getTotalItemsSold(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

}
