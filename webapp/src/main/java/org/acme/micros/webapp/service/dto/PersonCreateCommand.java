package org.acme.micros.webapp.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import static org.apache.commons.lang3.StringUtils.normalizeSpace;

public record PersonCreateCommand(@NotBlank(message = "Person's name is mandatory")
                                  @Size(min = 2, max = 150, message = "Person's name must have at least 2 characters and a maximum of 150")
                                  String name,
                                  @NotNull(message = "Person's birthdate is mandatory")
                                  @PastOrPresent(message = "Person's birthdate cannot be in the future")
                                  LocalDate birthDate) {

    public PersonCreateCommand(final String name, final LocalDate birthDate) {
        this.name = normalizeSpace(name);
        this.birthDate = birthDate;
    }
}
