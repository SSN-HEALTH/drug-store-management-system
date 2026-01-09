package com.ssnhealthcare.drugstore.distributor.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDistributorRequestDTO {

    @NotBlank(message = "Distributor name cannot be blank")
    private String distributorName;

    @NotBlank(message = "License number cannot be blank")
    private String licenseNumber;

    @NotBlank(message = "Contact number cannot be blank")
    private String contactNumber;

    @Email
    private String email;

    @NotBlank(message = "Address cannot be blank")
    private String address;
}
