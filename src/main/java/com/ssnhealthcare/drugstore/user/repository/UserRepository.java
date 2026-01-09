package com.ssnhealthcare.drugstore.user.repository;

import com.ssnhealthcare.drugstore.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
