package com.ssnhealthcare.drugstore.employee.dto.request;

import com.ssnhealthcare.drugstore.common.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRequestDTO {
    
    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number must not be blank")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    @Pattern(regexp = "\\d{10}", message = "Phone number must contain only digits")
    private String phone;

    @NotBlank(message = "Username must not be blank")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    @Pattern(
        regexp = "^[a-zA-Z0-9._-]+$",
        message = "Username can contain letters, numbers, dot, underscore and hyphen only"
    )
    private String username;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
        message = "Password must contain uppercase, lowercase, number and special character"
    )
    private String password;

    @NotNull(message = "Role is mandatory")
    private RoleType role;
}
