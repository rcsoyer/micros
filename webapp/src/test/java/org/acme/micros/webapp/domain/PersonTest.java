package org.acme.micros.webapp.domain;

import java.time.LocalDate;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PersonTest {

    @Test
    void testSanitizeOnInstantiation() {
        final var person = new Person("   sid    barret  ", LocalDate.now());

        assertEquals("sid barret", person.getName());
    }

    @Nested
    class EqualsAndHashCode {

        @Test
        void testEquals_whenNamesEqualThenTrue() {
            final var name = "Nina Simone";
            final var person1 = new Person(name.toLowerCase(), LocalDate.now());
            final var person2 = new Person(name.toUpperCase(), LocalDate.now());

            assertEquals(person1, person2);
        }

        @Test
        void testEquals_whenBothNullNamesThenFalse() {
            final var person1 = new Person(null, LocalDate.now());
            final var person2 = new Person(null, LocalDate.now());

            assertNotEquals(person1, person2);
        }

        @Test
        void testEquals_whenOneNullNameThenFalse() {
            final var person1 = new Person(null, LocalDate.now());
            final var person2 = new Person("Billy Joel", LocalDate.now());

            assertNotEquals(person1, person2);
        }

        @Test
        void testEquals_whenDifferentNamesThenFalse() {
            final var person1 = new Person("Friedrich Nietzsche", LocalDate.now());
            final var person2 = new Person("Zarathustra", LocalDate.now());

            assertNotEquals(person1, person2);
        }
    }

}