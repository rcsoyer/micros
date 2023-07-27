package org.acme.micros.cmd.runner.command.dto;

import java.time.LocalDate;

public record PersonCreateRequest(String name, LocalDate birthDate) {
}
