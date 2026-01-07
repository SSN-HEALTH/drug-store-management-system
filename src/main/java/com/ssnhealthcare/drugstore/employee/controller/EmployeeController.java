package com.ssnhealthcare.drugstore.employee.controller;

import com.ssnhealthcare.drugstore.employee.dto.request.EmployeeCreateRequestDTO;
import com.ssnhealthcare.drugstore.employee.dto.request.EmployeeUpdateRequestDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeCreateResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeGetResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeUpdateResponseDTO;
import com.ssnhealthcare.drugstore.employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<EmployeeCreateResponseDTO> createEmployee(
            @RequestBody EmployeeCreateRequestDTO dto) {

        EmployeeCreateResponseDTO response = employeeService.createEmployee(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeGetResponseDTO> getEmployeeById(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(employeeId)
        );
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<Page<EmployeeGetResponseDTO>> getAllEmployees(Pageable pageable) {
        return ResponseEntity.ok(employeeService.getAllEmployees(pageable));
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<EmployeeUpdateResponseDTO> updateMyProfile(
            @PathVariable Long employeeId,
            @RequestBody EmployeeUpdateRequestDTO dto) {

        return ResponseEntity.ok(
                employeeService.updateOwnProfile(employeeId, dto)
        );
    }


    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable Long employeeId) {

        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
