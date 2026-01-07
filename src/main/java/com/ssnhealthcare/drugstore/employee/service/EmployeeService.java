package com.ssnhealthcare.drugstore.employee.service;

import com.ssnhealthcare.drugstore.employee.dto.request.EmployeeCreateRequestDTO;
import com.ssnhealthcare.drugstore.employee.dto.request.EmployeeUpdateRequestDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeCreateResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeGetResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeStatusResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    EmployeeCreateResponseDTO createEmployee(EmployeeCreateRequestDTO dto);
    EmployeeGetResponseDTO getEmployeeById(Long id);
    Page<EmployeeGetResponseDTO> getAllEmployees(Pageable pageable);
    EmployeeStatusResponseDTO changeStatus(Long id, boolean active);
    EmployeeCreateResponseDTO updateOwnProfile(Long employeeId, EmployeeUpdateRequestDTO dto);

    String deleteEmployee(Long employeeId);
}
