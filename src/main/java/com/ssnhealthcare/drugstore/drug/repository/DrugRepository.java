package com.ssnhealthcare.drugstore.drug.repository;

import com.ssnhealthcare.drugstore.drug.entity.Drug;
import com.ssnhealthcare.drugstore.report.Dto.StockReportDto;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drug, Long>
{

    @Lock(LockModeType.PESSIMISTIC_WRITE)//lock the database execute one transaction at a time
    @Query("SELECT d FROM Drug d WHERE d.drugId = :drugId")
    Optional<Drug> findByIdForUpdate(Long drugId);





}
