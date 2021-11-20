package org.acme.micros.webapp.service.dto;

import java.time.LocalDate;

public record PersonCreateResponse(long id, String name, LocalDate birthDate) {
}
