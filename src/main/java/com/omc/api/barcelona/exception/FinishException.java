package com.omc.api.barcelona.exception;

public class FinishException extends Exception {

    public FinishException() {
    }

    public FinishException(String message) {
        super(message);
    }

    public FinishException(String message, Throwable cause) {
        super(message, cause);
    }

    public FinishException(Throwable cause) {
        super(cause);
    }

    public FinishException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }



}

