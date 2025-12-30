package com.capstoneproject.smartattendance.exception;

public class CustomeException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomeException(ErrorCode errorCode) {
        super(errorCode.getMessage()); 
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
