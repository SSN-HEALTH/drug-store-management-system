package com.ssnhealthcare.drugstore.employee.repository;

import com.ssnhealthcare.drugstore.employee.entity.Employee;
import com.ssnhealthcare.drugstore.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Optional<Employee> findByUser(User user);
}
