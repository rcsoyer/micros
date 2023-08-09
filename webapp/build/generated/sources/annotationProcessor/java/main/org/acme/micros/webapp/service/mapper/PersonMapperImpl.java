package org.acme.micros.webapp.service.mapper;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.acme.micros.webapp.domain.Person;
import org.acme.micros.webapp.service.dto.PersonCreateRequest;
import org.acme.micros.webapp.service.dto.PersonCreateResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-09T13:55:19+0200",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 17.0.6 (Eclipse Adoptium)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public Person from(PersonCreateRequest command) {
        if ( command == null ) {
            return null;
        }

        String name = null;
        LocalDate birthDate = null;

        name = command.name();
        birthDate = command.birthDate();

        Person person = new Person( name, birthDate );

        return person;
    }

    @Override
    public PersonCreateResponse to(Person person) {
        if ( person == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        LocalDate birthDate = null;

        if ( person.getId() != null ) {
            id = person.getId();
        }
        name = person.getName();
        birthDate = person.getBirthDate();

        PersonCreateResponse personCreateResponse = new PersonCreateResponse( id, name, birthDate );

        return personCreateResponse;
    }
}
