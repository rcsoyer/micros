package org.acme.micros.webapp.repository.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PersonFiltered {

    long getId();
    String getName();
    LocalDate getBirthDate();
    LocalDateTime getDateCreated();
    LocalDateTime getDateModified();
}
