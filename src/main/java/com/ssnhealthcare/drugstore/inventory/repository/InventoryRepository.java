package com.ssnhealthcare.drugstore.inventory.repository;

import com.ssnhealthcare.drugstore.drug.entity.Drug;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
