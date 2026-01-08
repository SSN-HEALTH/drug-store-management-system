package com.ssnhealthcare.drugstore.employee.service;

import com.ssnhealthcare.drugstore.employee.dto.request.EmployeeRequestDTO;
import com.ssnhealthcare.drugstore.employee.dto.request.EmployeeUpdateRequestDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.DeleteEmployeeResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeGetResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeUpdateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto);
    EmployeeResponseDTO getEmployeeById(Long id);
    Page<EmployeeResponseDTO> getAllEmployees(Pageable pageable);
    EmployeeUpdateResponseDTO updateOwnProfile(Long employeeId, EmployeeUpdateRequestDTO dto);
    DeleteEmployeeResponseDTO deleteEmployee(Long employeeId);
}
