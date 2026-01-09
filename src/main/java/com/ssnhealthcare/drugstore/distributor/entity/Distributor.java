package com.ssnhealthcare.drugstore.distributor.entity;

import com.ssnhealthcare.drugstore.common.enums.DistributorStatus;
import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "distributors")
public class Distributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distributor_id")
    private Long distributorId;

    @NotBlank
    @Column(name = "name", nullable = false, length = 30)
    private String distributorName;

    @NotBlank
    @Column(name = "license_number", length = 20)
    private String licenseNumber;

    @NotBlank
    @Column(name = "contact_number", length = 20)
    private String contactNumber;

    @Email
    @Column(name = "email", length = 30)
    private String email;

    @NotBlank
    @Column(name = "address", length = 200)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private DistributorStatus status;

}
