package com.ssnhealthcare.drugstore.customer.service.serviceImplementaions;

import com.ssnhealthcare.drugstore.customer.dto.request.CustomerRequestDTO;
import com.ssnhealthcare.drugstore.customer.dto.request.CustomerUpdateRequestDTO;
import com.ssnhealthcare.drugstore.customer.dto.request.GetAllCustomersRequestDto;
import com.ssnhealthcare.drugstore.customer.dto.response.CustomerResponseDTO;
import com.ssnhealthcare.drugstore.customer.dto.response.CustomerUpdateResponseDTO;
import com.ssnhealthcare.drugstore.customer.entity.Customer;
import com.ssnhealthcare.drugstore.customer.repository.CustomerRepository;
import com.ssnhealthcare.drugstore.customer.service.CustomerService;
import com.ssnhealthcare.drugstore.exception.CustomerNotFoundException;
import com.ssnhealthcare.drugstore.user.entity.User;
import com.ssnhealthcare.drugstore.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;


    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setActive(dto.getIsActive());
        userRepository.save(user);

        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        customer.setUser(user);

        customerRepository.save(customer);

        return mapToCustomerResponse(customer);
    }

    @Override
    @Transactional
    public CustomerResponseDTO getCustomerById(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found with id: " + customerId));

        return mapToCustomerResponse(customer);
    }

    @Override
    @Transactional
    public Page<CustomerResponseDTO> getAllCustomers(GetAllCustomersRequestDto dto) {

        Pageable pageable = PageRequest.of(
                dto.getPageNumber(),
                Math.min(dto.getSize(), 100),
                Sort.by(Sort.Direction.ASC, "name")
        );

        return customerRepository.findAll(pageable)
                .map(this::mapToCustomerResponse);
    }


    @Override
    public CustomerUpdateResponseDTO updateCustomer(
            Long customerId,
            CustomerUpdateRequestDTO dto) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found with id: " + customerId));

        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());

        return mapToUpdateResponse(customer);
    }


    private CustomerResponseDTO mapToCustomerResponse(Customer customer) {

        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());

        User user = customer.getUser();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());

        return dto;
    }

    private CustomerUpdateResponseDTO mapToUpdateResponse(Customer customer) {

        CustomerUpdateResponseDTO dto = new CustomerUpdateResponseDTO();
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        return dto;
    }
}
