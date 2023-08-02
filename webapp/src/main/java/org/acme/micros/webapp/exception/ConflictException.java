package org.acme.micros.webapp.exception;

import java.io.Serial;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
public class ConflictException extends IllegalArgumentException {

    @Serial
    private static final long serialVersionUID = 5765712549838931386L;

    public ConflictException(final String errorMessage) {
        super(errorMessage);
    }
}
