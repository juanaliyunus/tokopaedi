package com.enigmacamp.tokonyadia.utils.exeptions;

public class ValidationExeption extends RuntimeException {
    public ValidationExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
