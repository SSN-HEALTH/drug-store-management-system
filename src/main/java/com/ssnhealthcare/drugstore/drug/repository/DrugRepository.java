package com.ssnhealthcare.drugstore.drug.repository;

import com.ssnhealthcare.drugstore.drug.entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {

}
