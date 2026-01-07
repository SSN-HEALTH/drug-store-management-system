package com.ssnhealthcare.drugstore.user.dto.request;

import com.ssnhealthcare.drugstore.common.enums.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequestDTO {

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotNull(message = "Active status is required")
    private Boolean active;

    @NotNull(message = "Role is required")
    private RoleType role;
}

