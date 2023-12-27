package com.jm.jamesapp.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends AuthenticationException {

    public UnauthorizedException() {
        super("Acesso não autorizado.");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
