package com.ssnhealthcare.drugstore.exception;

import java.time.LocalDateTime;

public class ApiErrorResponse {

    private LocalDateTime timeStamp;
    private Integer status;
    private String message;
    private String path;
}
