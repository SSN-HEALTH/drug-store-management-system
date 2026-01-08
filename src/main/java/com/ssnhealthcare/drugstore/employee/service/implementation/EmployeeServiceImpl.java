package com.ssnhealthcare.drugstore.employee.service.implementation;

import com.ssnhealthcare.drugstore.employee.dto.request.EmployeeRequestDTO;
import com.ssnhealthcare.drugstore.employee.dto.request.EmployeeUpdateRequestDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.DeleteEmployeeResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeGetResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeUpdateResponseDTO;
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
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;


    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setActive(true);

        userRepository.save(user);

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setUser(user);

        employeeRepository.save(employee);

        return mapToEmployeeResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with id: " + id));

        return mapToEmployeeResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponseDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(this::mapToEmployeeResponse);
    }

    @Override
    public EmployeeUpdateResponseDTO updateOwnProfile(
            Long employeeId,
            EmployeeUpdateRequestDTO dto) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + employeeId));

        if (!employee.getEmail().equals(dto.getEmail())
                && employeeRepository.existsByEmail(dto.getEmail())) {
            throw new ResourceAlreadyExistException("Email already in use");
        }

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());

        return mapToEmployeeUpdateResponse(employee);
    }

    @Override
    public DeleteEmployeeResponseDTO deleteEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + employeeId));

        employeeRepository.delete(employee);

        DeleteEmployeeResponseDTO response = new DeleteEmployeeResponseDTO();
        response.setEmployeeId(employeeId);
        response.setMessage("Employee deleted successfully");

        return response;
    }


    // ================= MAPPERS =================
    private EmployeeResponseDTO mapToEmployeeResponse(Employee employee) {

        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setUsername(employee.getUser().getUsername());
        dto.setRole(employee.getUser().getRole());
        dto.setActive(employee.getUser().isActive());

        return dto;
    }

    private EmployeeUpdateResponseDTO mapToEmployeeUpdateResponse(Employee employee) {

        EmployeeUpdateResponseDTO dto = new EmployeeUpdateResponseDTO();
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());

        return dto;
    }
}
