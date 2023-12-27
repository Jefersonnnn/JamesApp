package com.jm.jamesapp.security.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Acesso não autorizado.");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
