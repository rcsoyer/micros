package org.acme.micros.webapp.service;

import java.time.LocalDate;
import java.util.Set;

import org.acme.micros.webapp.domain.Person;
import org.acme.micros.webapp.exception.ConflictException;
import org.acme.micros.webapp.repository.PersonRepository;
import org.acme.micros.webapp.service.dto.PersonCreateCommand;
import org.acme.micros.webapp.service.dto.PersonCreateResponse;
import org.acme.micros.webapp.service.mapper.PersonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @Mock
    private PersonMapper mapper;

    @Mock
    private BannedYearsDefinition bannedYearsDefinition;

    @InjectMocks
    private PersonService service;

    @Nested
    class Create {

        @Test
        void create_whenPersonDontExistsThenCreate() {
            final var name = "Arthur Dent";
            final var birthDate = LocalDate.now();
            final var personToBeCreated = new PersonCreateCommand(name, birthDate);
            final var person = new Person(name, birthDate);

            when(repository.existsByName(name)).thenReturn(false);
            when(mapper.from(personToBeCreated)).thenReturn(person);
            when(repository.save(person)).thenReturn(person);
            when(mapper.to(person)).thenReturn(new PersonCreateResponse(anyLong(), name, birthDate));

            final PersonCreateResponse response = service.create(personToBeCreated);

            verify(repository).existsByName(personToBeCreated.name());
            verify(mapper).from(personToBeCreated);
            verify(repository).save(person);
            verify(mapper).to(person);
            verifyNoMoreInteractions(repository, mapper);

            assertEquals(name, response.name());
            assertEquals(birthDate, response.birthDate());
            verifyNoInteractions(bannedYearsDefinition);
        }

        @Test
        void create_whenPersonExistsThenError() {
            final var personToBeCreated = new PersonCreateCommand("Arthur Dent", LocalDate.now());

            when(repository.existsByName(personToBeCreated.name())).thenReturn(true);

            Assertions.assertThrows(ConflictException.class,
                                    () -> service.create(personToBeCreated),
                                    "A person's name is unique. The system already have a person with the provided name");

            verify(repository).existsByName(personToBeCreated.name());
            verifyNoMoreInteractions(repository);
            verifyNoInteractions(mapper, bannedYearsDefinition);
        }
    }

    @Test
    void filterByBannedYears() {
        final var bannedYears = Set.of(1999, 1998);

        when(bannedYearsDefinition.bannedYearsFromFile())
            .thenReturn(bannedYears);

        service.filterByBannedYears();

        verify(bannedYearsDefinition).bannedYearsFromFile();
        verify(repository).findByNotIn(bannedYears);
        verifyNoMoreInteractions(repository, bannedYearsDefinition);
        verifyNoInteractions(mapper);
    }
}