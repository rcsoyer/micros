package org.acme.micros.webapp.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.acme.micros.webapp.domain.Person;
import org.acme.micros.webapp.repository.projection.PersonFiltered;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.time.Month.APRIL;
import static java.time.Month.FEBRUARY;
import static java.time.Month.MARCH;
import static java.time.Month.MAY;
import static org.assertj.core.api.Assertions.assertThat;

class PersonRepositoryTestIT extends BaseRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    void findByNotIn() {
        final int year = 1939;
        final int bannedYear1 = 1964;
        final int bannedYear2 = 1965;
        repository.saveAll(List.of(
            new Person("bruce wayne", LocalDate.of(year, MAY, 1)),
            new Person("clark kent", LocalDate.of(year, APRIL, 1)),
            new Person("peter parker", LocalDate.of(bannedYear1, MARCH, 1)),
            new Person("bruce banner", LocalDate.of(bannedYear2, FEBRUARY, 1))));
        final var bannedYears = Set.of(bannedYear1, bannedYear2);

        final List<PersonFiltered> result = repository.findByNotIn(bannedYears);

        assertThat(result)
            .noneMatch(person -> bannedYears.contains(person.getBirthDate().getYear()));
    }
}