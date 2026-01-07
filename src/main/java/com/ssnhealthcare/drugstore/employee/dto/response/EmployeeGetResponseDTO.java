package com.ssnhealthcare.drugstore.employee.dto.response;

import com.ssnhealthcare.drugstore.common.enums.RoleType;
import lombok.Data;

@Data
public class EmployeeGetResponseDTO {
    private Long employeeId;
    private String name;
    private String email;
    private String phone;
    private String username;

}
