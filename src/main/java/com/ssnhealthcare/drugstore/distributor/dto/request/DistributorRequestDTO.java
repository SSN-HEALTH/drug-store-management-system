package com.ssnhealthcare.drugstore.distributor.dto.request;

import com.ssnhealthcare.drugstore.common.enums.DistributorStatus;
import com.ssnhealthcare.drugstore.distributor.entity.Distributor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DistributorRequestDTO {

    @NotNull (message = "Distributor cannot be null")
    private Distributor distributor;

//    @NotNull (message = "distributor Id cannot be null")
//    private Long distributorId;

    @NotBlank (message = "Distributor Name cannot be blank")
    private String distributorName;

    @NotBlank (message = "License number cannot be blank")
    private String licenseNumber;

    @NotBlank (message = "Contact Number cannot be blank")
    private String contactNumber;

    @Email
    private String email;

    @NotBlank (message = "Address cannot be blank")
    private String address;

    @Enumerated(EnumType.STRING)
    private DistributorStatus status;

    @NotNull (message = "Page number cannot be empty")
    private int pageNumber;

    @NotNull (message = "Size cannot be empty")
    private int size;
}
