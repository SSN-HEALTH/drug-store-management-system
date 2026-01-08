package com.ssnhealthcare.drugstore.user.dto.responce;

import com.ssnhealthcare.drugstore.common.enums.RoleType;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long userId;
    private String username;
    private boolean active;
    private RoleType role;
}
