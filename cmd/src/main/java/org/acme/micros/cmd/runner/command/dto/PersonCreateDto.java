package org.acme.micros.cmd.runner.command.dto;

import java.time.LocalDate;

public record PersonCreateDto(String name, LocalDate birthDate) {
}
