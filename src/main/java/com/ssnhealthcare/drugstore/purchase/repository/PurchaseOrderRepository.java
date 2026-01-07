package com.ssnhealthcare.drugstore.purchase.repository;

import com.ssnhealthcare.drugstore.purchase.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository <PurchaseOrder,Long> {
}
