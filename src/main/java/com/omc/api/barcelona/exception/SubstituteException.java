package com.omc.api.barcelona.exception;

public class SubstituteException extends Exception {

    public SubstituteException() {
    }

    public SubstituteException(String message) {
        super(message);
    }

    public SubstituteException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubstituteException(Throwable cause) {
        super(cause);
    }

    public SubstituteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}