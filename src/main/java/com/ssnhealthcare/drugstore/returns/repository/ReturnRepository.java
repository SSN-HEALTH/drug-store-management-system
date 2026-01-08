package com.ssnhealthcare.drugstore.returns.repository;

import com.ssnhealthcare.drugstore.returns.entity.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Long> {
}
