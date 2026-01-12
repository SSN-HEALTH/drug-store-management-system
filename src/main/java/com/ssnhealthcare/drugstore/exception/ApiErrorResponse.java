package com.ssnhealthcare.drugstore.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@AllArgsConstructor
@Data
public class ApiErrorResponse {

    private LocalDateTime timeStamp;
    private Integer status;
    private String message;
    private String path;
}
