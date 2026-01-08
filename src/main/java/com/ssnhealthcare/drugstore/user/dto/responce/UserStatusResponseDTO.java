package com.ssnhealthcare.drugstore.user.dto.responce;

import lombok.Data;

@Data
public class UserStatusResponseDTO {
    private Long userId;
    private boolean active;
    private String message;
}
