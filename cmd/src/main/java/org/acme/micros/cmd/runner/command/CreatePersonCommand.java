package org.acme.micros.cmd.runner.command;

import java.time.LocalDate;

import org.acme.micros.cmd.client.webapp.PersonClient;
import org.acme.micros.cmd.runner.command.dto.PersonCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * The {@link CommandLine} to create a Person from the provided required arguments: {@link
 * CreatePersonCommand#personName} and {@link CreatePersonCommand#personBirthDate}.
 * <p> The person is created on the microservice webapp via REST API call and then its fetched from the same
 * microservice all the stored Persons not in the banned years
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Command(name = "createPerson", description = "Create a Person via REST API to a microservice")
public class CreatePersonCommand implements Runnable {

    private final PersonClient apiClient;

    @Option(names = "-n", required = true, paramLabel = "name")
    private String personName;

    @Option(names = "-b", required = true, paramLabel = "birthDate")
    private LocalDate personBirthDate;

    @Override
    public void run() {
        log.info("Name was: {}", personName);
        log.info("BirthDate was: {}", personBirthDate);
        apiClient.createPerson(new PersonCreateDto(personName, personBirthDate));
        log.info("All the people not banned: {}", apiClient.findPeopleNotBanned());
    }
}
