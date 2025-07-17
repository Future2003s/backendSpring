package com.shopdev.exception;


import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler implements Serializable {
    @ExceptionHandler(value = SQLException.class)
    public ErrorResponse handleSQLException(SQLException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(500);
        errorResponse.setTimestamp(new Date(System.currentTimeMillis()));
        return errorResponse;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(500);
        errorResponse.setTimestamp(new Date(System.currentTimeMillis()));
        return errorResponse;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        String message = ex.getFieldError().getDefaultMessage();
        errorResponse.setMessage(message);
        errorResponse.setStatus(500);
        errorResponse.setTimestamp(new Date(System.currentTimeMillis()));
        return errorResponse;
    }


    @ExceptionHandler(value = UnexpectedTypeException.class)
    public ErrorResponse handleUnexpectedTypeException(UnexpectedTypeException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        String message = ex.getMessage();
        errorResponse.setMessage(message);
        errorResponse.setStatus(500);
        errorResponse.setTimestamp(new Date(System.currentTimeMillis()));
        return errorResponse;
    }
}
