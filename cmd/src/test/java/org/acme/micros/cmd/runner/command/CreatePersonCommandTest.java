package org.acme.micros.cmd.runner.command;

import java.time.LocalDate;

import org.acme.micros.cmd.client.webapp.PersonClient;
import org.acme.micros.cmd.runner.command.dto.PersonCreateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class CreatePersonCommandTest {

    @Mock
    private PersonClient personClient;

    @InjectMocks
    private CreatePersonCommand createPersonCommand;

    @Captor
    private ArgumentCaptor<PersonCreateDto> personCaptor;

    @Test
    void run() {
        final var personName = "Arthur C. Clarke";
        final var personBirthDate = LocalDate.now();
        ReflectionTestUtils.setField(createPersonCommand, "personName", personName);
        ReflectionTestUtils.setField(createPersonCommand, "personBirthDate", personBirthDate);

        createPersonCommand.run();

        verify(personClient).createPerson(personCaptor.capture());
        final PersonCreateDto person = personCaptor.getValue();
        assertEquals(personName, person.name());
        assertEquals(personBirthDate, person.birthDate());

        verify(personClient).findPeopleNotBanned();
        verifyNoMoreInteractions(personClient);
    }
}