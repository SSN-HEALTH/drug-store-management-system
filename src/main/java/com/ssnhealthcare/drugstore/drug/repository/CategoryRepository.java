package com.ssnhealthcare.drugstore.drug.repository;

import com.ssnhealthcare.drugstore.drug.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{
}
