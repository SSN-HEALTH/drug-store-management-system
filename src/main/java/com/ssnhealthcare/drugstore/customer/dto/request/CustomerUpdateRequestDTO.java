package com.ssnhealthcare.drugstore.customer.dto.request;

import lombok.Data;

@Data
public class CustomerUpdateRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String address;


}
