package org.acme.micros.webapp.service.dto;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonCreateRequestTest {

    @Test
    void testSanitizeOnInstantiation() {
        final var person = new PersonCreateRequest("   sid    barret  ", LocalDate.now());

        assertEquals("sid barret", person.name());
    }

}