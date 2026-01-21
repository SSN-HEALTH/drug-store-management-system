package com.ssnhealthcare.drugstore.employee.repository;

import com.ssnhealthcare.drugstore.common.enums.RoleType;
import com.ssnhealthcare.drugstore.employee.entity.Employee;
import com.ssnhealthcare.drugstore.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Optional<Employee> findByUser(User user);

    @Query("""
        select e.email from Employee e
        join e.user u
        where u.role in :roles and e.email is not null
    """)
    List<String> findEmailsByRoles(List<RoleType> roles);

}
