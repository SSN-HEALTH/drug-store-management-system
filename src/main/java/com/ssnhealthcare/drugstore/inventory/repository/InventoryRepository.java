package com.ssnhealthcare.drugstore.inventory.repository;

import com.ssnhealthcare.drugstore.drug.entity.Drug;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
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

    Optional<Inventory> findByDrug(Drug drug);

    boolean existsByDrug(Drug drug);

    List<Inventory> findByExpiryDateBefore(LocalDate date);


    @Query("""
                SELECT i FROM Inventory i
                WHERE i.expiryDate <= :thresholdDate
                AND i.quantity > 0
            """)
    Page<Inventory> findExpiringInventory(
            @Param("thresholdDate") LocalDate thresholdDate,
            Pageable pageable
    );

}
