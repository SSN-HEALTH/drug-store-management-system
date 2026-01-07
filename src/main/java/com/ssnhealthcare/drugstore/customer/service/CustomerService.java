package com.ssnhealthcare.drugstore.customer.service;

import com.ssnhealthcare.drugstore.customer.dto.request.CreateCustomerRequestDTO;
import com.ssnhealthcare.drugstore.customer.dto.request.CustomerUpdateRequestDTO;
import com.ssnhealthcare.drugstore.customer.dto.request.GetAllCustomersRequestDto;
import com.ssnhealthcare.drugstore.customer.dto.response.CreateCustomerResponseDTO;
import com.ssnhealthcare.drugstore.customer.dto.response.CustomerUpdateResponseDTO;
import com.ssnhealthcare.drugstore.customer.dto.response.GetAllCustomerResponseDTO;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;

public interface CustomerService {


     Page<GetAllCustomerResponseDTO> getAllCustomers(GetAllCustomersRequestDto dto);

     CreateCustomerResponseDTO createCustomer(@Valid CreateCustomerRequestDTO dto);

     GetAllCustomerResponseDTO getCustomerById(Long customerId);

     CustomerUpdateResponseDTO updateCustomer(Long customerId, @Valid CustomerUpdateRequestDTO dto);
}
