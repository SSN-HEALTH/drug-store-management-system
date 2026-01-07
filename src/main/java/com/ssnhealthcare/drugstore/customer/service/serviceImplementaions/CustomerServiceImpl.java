package com.ssnhealthcare.drugstore.customer.service.serviceImplementaions;

import com.ssnhealthcare.drugstore.customer.dto.request.CreateCustomerRequestDTO;
import com.ssnhealthcare.drugstore.customer.dto.request.CustomerUpdateRequestDTO;
import com.ssnhealthcare.drugstore.customer.dto.request.GetAllCustomersRequestDto;
import com.ssnhealthcare.drugstore.customer.dto.response.CreateCustomerResponseDTO;
import com.ssnhealthcare.drugstore.customer.dto.response.CustomerUpdateResponseDTO;
import com.ssnhealthcare.drugstore.customer.dto.response.GetAllCustomerResponseDTO;
import com.ssnhealthcare.drugstore.customer.entity.Customer;
import com.ssnhealthcare.drugstore.customer.repository.CustomerRepository;
import com.ssnhealthcare.drugstore.customer.service.CustomerService;
import com.ssnhealthcare.drugstore.exception.CustomerNotFoundException;
import com.ssnhealthcare.drugstore.exception.UserNotFoundException;
import com.ssnhealthcare.drugstore.user.entity.User;
import com.ssnhealthcare.drugstore.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    UserRepository userRepository;
    CustomerRepository customerRepository;

    @Override
    public CreateCustomerResponseDTO createCustomer(CreateCustomerRequestDTO dto) {
        User user=new User();
        user.setRole(dto.getRole());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setActive(dto.getIsActive());
            userRepository.save(user);
        Customer customer=new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        customer.setUser(user);
            Customer savedCustomer=customerRepository.save(customer);
        return mapToCreateResponseDTO(savedCustomer);


    }



    private CreateCustomerResponseDTO mapToCreateResponseDTO(Customer customer) {

        CreateCustomerResponseDTO dto = new CreateCustomerResponseDTO();

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
    @Override
    public GetAllCustomerResponseDTO getCustomerById(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException
                        ("Customer not found with id: " + customerId));

        return mapToResponseDto(customer);
    }


    //------------------------------------------------------------------------------
@Override
public Page<GetAllCustomerResponseDTO> getAllCustomers(GetAllCustomersRequestDto dto) {

    int page = dto.getPageNumber();
    int size = Math.min(dto.getSize(), 100);

    Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(Sort.Direction.ASC, "name")
    );

    return customerRepository.findAll(pageable)
            .map(this::mapToResponseDto);
}


    private GetAllCustomerResponseDTO mapToResponseDto(Customer customer) {

        GetAllCustomerResponseDTO dto = new GetAllCustomerResponseDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());

        return dto;
    }
//-----------------------------------------------------------------------------------------------------------------
@Transactional
    @Override
public CustomerUpdateResponseDTO updateCustomer(Long customerId, CustomerUpdateRequestDTO dto) {

    Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException
                    ("Customer not found with id: " + customerId));
    customer.setName(dto.getName());
    customer.setEmail(dto.getEmail());
    customer.setPhone(dto.getPhone());
    customer.setAddress(dto.getAddress());
    Customer customer1=customerRepository.save(customer);

    return mapToUpdateResponse(customer1);
}
public CustomerUpdateResponseDTO mapToUpdateResponse(Customer customer){
        CustomerUpdateResponseDTO dto=new CustomerUpdateResponseDTO();
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        return dto;

}
}
