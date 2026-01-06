package com.ssnhealthcare.drugstore.sale.repository;

import com.ssnhealthcare.drugstore.sale.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem,Long>
{
    // Fetch all items for a sale (Invoice view)
    List<SaleItem> findBySale_SaleId(Long saleId);

    // Fetch sale items by drug (Stock movement / analytics)
    List<SaleItem> findByDrug_DrugId(Long drugId);
}
