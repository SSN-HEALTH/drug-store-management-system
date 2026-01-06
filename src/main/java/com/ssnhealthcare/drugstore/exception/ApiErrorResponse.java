package com.ssnhealthcare.drugstore.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private Integer status;
    private String message;
    private String path;
}
