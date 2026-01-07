package com.ssnhealthcare.drugstore.employee.service.implementation;

import com.ssnhealthcare.drugstore.employee.dto.request.EmployeeCreateRequestDTO;
import com.ssnhealthcare.drugstore.employee.dto.request.EmployeeUpdateRequestDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeCreateResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeGetResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeStatusResponseDTO;
import com.ssnhealthcare.drugstore.employee.entity.Employee;
import com.ssnhealthcare.drugstore.employee.repository.EmployeeRepository;
import com.ssnhealthcare.drugstore.employee.service.EmployeeService;
import com.ssnhealthcare.drugstore.exception.EmployeeNotFoundException;
import com.ssnhealthcare.drugstore.exception.ResourceAlreadyExistException;
import com.ssnhealthcare.drugstore.user.entity.User;
import com.ssnhealthcare.drugstore.user.repository.UserRepository;
import com.ssnhealthcare.drugstore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    @Transactional
    public EmployeeCreateResponseDTO createEmployee(EmployeeCreateRequestDTO dto) {

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setActive(true);
        User savedUser = userRepository.save(user);

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setUser(savedUser);

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToEmployeeCreateResponseDTO(savedEmployee);
    }

    @Override
    public EmployeeGetResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        return mapToEmployeeGetResponse(employee);
    }

    @Override
    public Page<EmployeeGetResponseDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(this::mapToEmployeeGetResponse);
    }

    @Override
    @Transactional
    public EmployeeStatusResponseDTO changeStatus(Long id, boolean active) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        User user = employee.getUser();
        user.setActive(active);

        EmployeeStatusResponseDTO response = new EmployeeStatusResponseDTO();
        response.setEmployeeId(employee.getEmployeeId());
        response.setActive(active);
        response.setMessage("Employee status updated successfully");

        return response;
    }

    @Override
    public EmployeeCreateResponseDTO updateOwnProfile(Long employeeId, EmployeeUpdateRequestDTO dto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));
        if (!employee.getEmail().equals(dto.getEmail()) &&
                employeeRepository.existsByEmail(dto.getEmail())) {
            throw new ResourceAlreadyExistException("Email already in use");
        }

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());

        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToEmployeeCreateResponseDTO(updatedEmployee);
    }

    @Override
    public String deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + employeeId));

        employeeRepository.delete(employee);
        return "Employee deleted successfully";
    }


    private EmployeeCreateResponseDTO mapToEmployeeCreateResponseDTO(Employee employee) {
        EmployeeCreateResponseDTO dto = new EmployeeCreateResponseDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setUsername(employee.getUser().getUsername());
        dto.setRole(employee.getUser().getRole());
        dto.setActive(employee.getUser().isActive());
        return dto;
    }

    private EmployeeGetResponseDTO mapToEmployeeGetResponse(Employee employee) {
        EmployeeGetResponseDTO dto = new EmployeeGetResponseDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setUsername(employee.getUser().getUsername());
        return dto;
    }
}
