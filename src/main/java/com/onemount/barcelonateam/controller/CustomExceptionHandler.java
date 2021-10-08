package com.onemount.barcelonateam.controller;

import com.onemount.barcelonateam.exceptions.TeamException;
import com.onemount.barcelonateam.exceptions.TypeMismatchException;
import com.onemount.barcelonateam.model.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public final ResponseEntity<ApiError> handleAllExceptions(Exception ex) {
        ApiError ApiError = new ApiError("Generic Exception", ex.getLocalizedMessage(), ex.getCause().getMessage());
        return new ResponseEntity<>(ApiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(TeamException.class)
    public final ResponseEntity<ApiError> handleUserNotFoundException(TeamException ex, WebRequest request) {
        ApiError ApiError = new ApiError(ex);
        return new ResponseEntity<>(ApiError, HttpStatus.BAD_REQUEST);
    }

//    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status,
                                                        WebRequest request) {
        ApiError error = new ApiError("Argument type mismatch", ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {

        ApiError error = new ApiError("No handler found", ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
