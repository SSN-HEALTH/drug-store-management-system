package com.ssnhealthcare.drugstore.distributor.repository;

import com.ssnhealthcare.drugstore.common.enums.DistributorStatus;
import com.ssnhealthcare.drugstore.distributor.entity.Distributor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DistributorRepository extends JpaRepository <Distributor,Long> {


    boolean existsByLicenseNumber(String licenseNumber);
    Page<Distributor> findByStatus(DistributorStatus status, Pageable pageable);


}
