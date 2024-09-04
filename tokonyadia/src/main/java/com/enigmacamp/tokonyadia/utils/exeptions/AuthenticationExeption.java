package com.enigmacamp.tokonyadia.utils.exeptions;

public class AuthenticationExeption extends RuntimeException {
    public AuthenticationExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
