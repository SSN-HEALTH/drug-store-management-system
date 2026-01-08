package com.ssnhealthcare.drugstore.purchase.repository;

import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;
import com.ssnhealthcare.drugstore.distributor.entity.Distributor;
import com.ssnhealthcare.drugstore.purchase.entity.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PurchaseOrderRepository extends JpaRepository <PurchaseOrder,Long> {

    Page<PurchaseOrder> findByOrderDateTimeBetween(
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);

    boolean existsByDistributorIdAndStatus(
            Long distributorId,
            PurchaseStatus status);

}


