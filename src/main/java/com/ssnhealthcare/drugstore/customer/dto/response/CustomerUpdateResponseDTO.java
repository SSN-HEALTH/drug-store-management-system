package com.ssnhealthcare.drugstore.customer.dto.response;

import lombok.Data;

@Data
public class CustomerUpdateResponseDTO {
    private String name;
    private String email;
    private String phone;
    private String address;

}
