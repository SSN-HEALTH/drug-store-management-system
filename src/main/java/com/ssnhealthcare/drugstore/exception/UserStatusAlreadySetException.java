package com.ssnhealthcare.drugstore.exception;

public class UserStatusAlreadySetException extends RuntimeException {
    public UserStatusAlreadySetException(String message) {
        super(message);
    }
}
