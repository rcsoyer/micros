package org.acme.micros.cmd;

import java.time.LocalDate;

import org.acme.micros.cmd.client.webapp.PersonClient;
import org.acme.micros.cmd.runner.command.dto.PersonCreateRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@ActiveProfiles("inttest")
@SpringBootTest(webEnvironment = NONE, classes = {CmdApp.class, CmdAppTestIT.TestConfig.class},
                args = {"-n=Nat Buck", "-b=2021-10-15"})
class CmdAppTestIT {

    @Autowired
    private PersonClient personClient;

    @Captor
    private ArgumentCaptor<PersonCreateRequest> personCaptor;

    @Test
    void main() {
        verify(personClient).createPerson(personCaptor.capture());
        final PersonCreateRequest person = personCaptor.getValue();
        assertEquals("Nat Buck", person.name());
        assertEquals(LocalDate.parse("2021-10-15"), person.birthDate());

        verify(personClient).findPeopleNotBanned();
        verifyNoMoreInteractions(personClient);
    }

    @Configuration
    static class TestConfig {

        @MockBean
        private PersonClient personClient;
    }
}