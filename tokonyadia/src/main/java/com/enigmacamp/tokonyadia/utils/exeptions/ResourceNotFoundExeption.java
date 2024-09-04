package com.enigmacamp.tokonyadia.utils.exeptions;

public class ResourceNotFoundExeption extends RuntimeException {
    public ResourceNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
