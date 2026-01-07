package com.ssnhealthcare.drugstore.customer.dto.response;

import com.ssnhealthcare.drugstore.common.enums.RoleType;
import lombok.Data;

@Data
public class CreateCustomerResponseDTO {

    private Long customerId;
    private String name;
    private String email;
    private String phone;
    private String address;

    private Long userId;
    private String username;
    private RoleType role;
    private boolean active;
}

