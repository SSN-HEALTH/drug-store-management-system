package com.ssnhealthcare.drugstore.sale.repository;

import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import com.ssnhealthcare.drugstore.sale.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale,Long>
{
    Page<Sale> findByProcessedBy_UserId(Long userId, Pageable pageable);


    Page<Sale> findBySaleDateBetween(
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable
    );
}
