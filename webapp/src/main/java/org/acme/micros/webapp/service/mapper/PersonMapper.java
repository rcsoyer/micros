package org.acme.micros.webapp.service.mapper;

import org.acme.micros.webapp.domain.Person;
import org.acme.micros.webapp.service.dto.PersonCreateRequest;
import org.acme.micros.webapp.service.dto.PersonCreateResponse;
import org.mapstruct.Mapper;

/**
 * Maps/transforms the domain entity {@link Person} to and from its DTOs
 */
@Mapper
public interface PersonMapper {

    Person from(PersonCreateRequest command);

    PersonCreateResponse to(Person person);
}
