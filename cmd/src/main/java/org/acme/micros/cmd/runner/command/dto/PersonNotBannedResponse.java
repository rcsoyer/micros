package org.acme.micros.cmd.runner.command.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PersonNotBannedResponse(long id, String name, LocalDate birthDate, LocalDateTime dateCreated,
                                      LocalDateTime dateModified) {
}
