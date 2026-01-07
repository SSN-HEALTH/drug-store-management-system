package com.ssnhealthcare.drugstore.employee.dto.response;

import lombok.Data;

@Data
public class EmployeeStatusResponseDTO {
    private Long employeeId;
    private boolean active;
    private String message;
}
