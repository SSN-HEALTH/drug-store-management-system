package com.ssnhealthcare.drugstore.customer.dto.response;

import lombok.Data;

@Data
public class GetAllCustomerResponseDTO {

    private Long customerId;
    private String name;
    private String email;
    private String phone;
    private boolean active;
}

