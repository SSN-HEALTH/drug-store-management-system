package com.ssnhealthcare.drugstore.employee.controller;

import com.ssnhealthcare.drugstore.employee.dto.request.EmployeeRequestDTO;
import com.ssnhealthcare.drugstore.employee.dto.request.EmployeeUpdateRequestDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.DeleteEmployeeResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeGetResponseDTO;
import com.ssnhealthcare.drugstore.employee.dto.response.EmployeeUpdateResponseDTO;
import com.ssnhealthcare.drugstore.employee.service.EmployeeService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @RequestBody EmployeeRequestDTO dto) {

        EmployeeResponseDTO response = employeeService.createEmployee(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/getEmployee/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(employeeId)
        );
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployees(Pageable pageable) {
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
    public ResponseEntity<DeleteEmployeeResponseDTO> deleteEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(employeeService.deleteEmployee(employeeId));
    }

}
