package com.ssnhealthcare.drugstore.customer.repository;

import com.ssnhealthcare.drugstore.customer.entity.Customer;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
