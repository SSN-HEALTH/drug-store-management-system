package com.ssnhealthcare.drugstore.purchase.repository;

import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;

import com.ssnhealthcare.drugstore.purchase.entity.PurchaseOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
@Repository
public interface PurchaseOrderRepository extends JpaRepository <PurchaseOrder,Long> {

    Page<PurchaseOrder> findByOrderDateTimeBetween(
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);

    boolean existsByDistributorId_DistributorIdAndStatus(Long id, PurchaseStatus purchaseStatus);
}


