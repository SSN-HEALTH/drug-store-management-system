package com.ssnhealthcare.drugstore.distributor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "distributors")
public class Distributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distributor_id")
    private Long distributorId;

    @NotBlank
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @NotBlank
    @Column(name = "contact_number", length = 20)
    private String contactNumber;

    @Email
    @Column(name = "email", length = 30)
    private String email;

    @NotBlank
    @Column(name = "address", length = 200)
    private String address;
}
