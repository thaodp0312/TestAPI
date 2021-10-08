package com.omc.api.barcelona.exception;

public class AvailableException extends Exception {

    public AvailableException() {
    }

    public AvailableException(String message) {
        super(message);
    }

    public AvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public AvailableException(Throwable cause) {
        super(cause);
    }

    public AvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }



}