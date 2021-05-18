package com.kwetter.frits.userservice.exception;

public class ExceptionMessage extends RuntimeException {

    private String message;

    public ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
