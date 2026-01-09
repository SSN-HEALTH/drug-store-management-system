package com.ssnhealthcare.drugstore.exception;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
public class ApiErrorResponse {

    private LocalDateTime timeStamp;
    private Integer status;
    private String message;
    private String path;
}
