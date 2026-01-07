package com.ssnhealthcare.drugstore.customer.entity;


import com.ssnhealthcare.drugstore.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @NotBlank
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Size(min = 10, max = 10)
    @Column(name = "phone", length = 10)
    private String phone;

    @Column(nullable = false)
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

