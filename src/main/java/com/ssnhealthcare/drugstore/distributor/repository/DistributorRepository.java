package com.ssnhealthcare.drugstore.distributor.repository;

import com.ssnhealthcare.drugstore.distributor.entity.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.ScopedValue;

public interface DistributorRepository extends JpaRepository <Distributor,Long> {

}
