package com.onemount.barcelonateam.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    public String message;
    public List<String> details;

    public ApiError(String message, String ... details) {
        this.message = message;
        this.details = Arrays.asList(details);
    }

    public ApiError(Exception ex) {
        String classNameOfException = ex.getClass().getName();
        int lastDotIndex = classNameOfException.lastIndexOf(".");
        String shortExceptionName;
        if (lastDotIndex > 0) {
            shortExceptionName = classNameOfException.substring(lastDotIndex + 1);
        } else {
            shortExceptionName = classNameOfException;
        }

        this.message = shortExceptionName + " : " + ex.getMessage();
        if (ex.getCause() != null) {
            this.details = new ArrayList<>();
            this.details.add(ex.getCause().getMessage());
        }
    }
}