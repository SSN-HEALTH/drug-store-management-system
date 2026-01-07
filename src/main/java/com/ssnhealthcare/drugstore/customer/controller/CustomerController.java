package com.ssnhealthcare.drugstore.customer.controller;

import com.ssnhealthcare.drugstore.customer.dto.request.CreateCustomerRequestDTO;
import com.ssnhealthcare.drugstore.customer.dto.request.CustomerUpdateRequestDTO;
import com.ssnhealthcare.drugstore.customer.dto.request.GetAllCustomersRequestDto;
import com.ssnhealthcare.drugstore.customer.dto.response.CreateCustomerResponseDTO;
import com.ssnhealthcare.drugstore.customer.dto.response.CustomerUpdateResponseDTO;
import com.ssnhealthcare.drugstore.customer.dto.response.GetAllCustomerResponseDTO;
import com.ssnhealthcare.drugstore.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<CreateCustomerResponseDTO> createCustomer(@Valid @RequestBody CreateCustomerRequestDTO dto) {

        return ResponseEntity.ok(customerService.createCustomer(dto));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<GetAllCustomerResponseDTO> getCustomerById(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<Page<GetAllCustomerResponseDTO>> getAllCustomers(@Valid @ModelAttribute GetAllCustomersRequestDto dto) {
        return ResponseEntity.ok(customerService.getAllCustomers(dto));
    }


    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerUpdateResponseDTO> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerUpdateRequestDTO dto) {

        return ResponseEntity.ok(
                customerService.updateCustomer(customerId, dto)
        );
    }


}

