package org.acme.micros.webapp.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
public class ConflictException extends IllegalArgumentException {

    public ConflictException(final String errorMessage) {
        super(errorMessage);
    }
}
