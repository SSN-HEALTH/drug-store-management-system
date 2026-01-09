package com.ssnhealthcare.drugstore.distributor.dto.response;

import com.ssnhealthcare.drugstore.common.enums.DistributorStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DistributorResponseDTO {

    private Long distributorId;
    private String distributorName;
    private String contactNumber;
    private String email;
    private String address;
    private DistributorStatus status;
    private String licenseNumber;
}
