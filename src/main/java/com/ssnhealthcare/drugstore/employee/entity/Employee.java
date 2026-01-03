package com.ssnhealthcare.drugstore.employee.entity;

import com.ssnhealthcare.drugstore.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @NotBlank
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Email
    @Column(name = "email", unique = true, length = 30)
    private String email;

    @Size(min = 10, max = 10)
    @Column(name = "phone", length = 10)
    private String phone;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
