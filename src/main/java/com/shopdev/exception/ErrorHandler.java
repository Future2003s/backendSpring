package com.shopdev.exception;


public class ErrorHandler extends RuntimeException {
    public ErrorHandler(String message) {
        super(message);
    }
}
