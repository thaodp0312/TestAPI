package com.onemount.barcelonateam.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeamException extends RuntimeException {

    private String message;
    private String details;

    public TeamException() {
    }

    public TeamException(String message, String details) {
        super(message);
        this.message = message;
        this.details = details;
    }
}
