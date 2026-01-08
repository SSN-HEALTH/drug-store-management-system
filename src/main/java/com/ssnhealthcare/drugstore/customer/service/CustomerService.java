package com.ssnhealthcare.drugstore.customer.service;

import com.ssnhealthcare.drugstore.customer.dto.request.CustomerRequestDTO;
import com.ssnhealthcare.drugstore.customer.dto.request.CustomerUpdateRequestDTO;
import com.ssnhealthcare.drugstore.customer.dto.request.GetAllCustomersRequestDto;
import com.ssnhealthcare.drugstore.customer.dto.response.CustomerResponseDTO;
import com.ssnhealthcare.drugstore.customer.dto.response.CustomerUpdateResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface CustomerService {


     Page<CustomerResponseDTO> getAllCustomers(GetAllCustomersRequestDto dto);

     CustomerResponseDTO createCustomer(@Valid CustomerRequestDTO dto);

     CustomerResponseDTO getCustomerById(Long customerId);

     CustomerUpdateResponseDTO updateCustomer(Long customerId, @Valid CustomerUpdateRequestDTO dto);
}
