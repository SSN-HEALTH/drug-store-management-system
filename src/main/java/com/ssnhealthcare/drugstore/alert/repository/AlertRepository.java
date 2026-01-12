package com.ssnhealthcare.drugstore.alert.repository;

import com.ssnhealthcare.drugstore.alert.entity.Alert;
import com.ssnhealthcare.drugstore.common.enums.AlertType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    boolean existsByReferenceIdAndAlertTypeAndResolvedFalse(
            Long referenceId,
            AlertType alertType
    );

    Page<Alert> findByResolvedFalse(Pageable pageable);
}
