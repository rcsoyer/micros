package org.acme.micros.webapp.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.micros.webapp.domain.Person;
import org.acme.micros.webapp.exception.ConflictException;
import org.acme.micros.webapp.repository.PersonRepository;
import org.acme.micros.webapp.repository.projection.PersonFiltered;
import org.acme.micros.webapp.service.dto.PersonCreateRequest;
import org.acme.micros.webapp.service.dto.PersonCreateResponse;
import org.acme.micros.webapp.service.mapper.PersonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {

    private final PersonMapper mapper;
    private final PersonRepository repository;
    private final BannedYearsDefinition bannedYears;

    /**
     * Creates an unique {@link Person}
     *
     * @throws ConflictException if a {@link Person} with same name already exists in the
     *                           app
     */
    public PersonCreateResponse create(final PersonCreateRequest person) {
        log.debug("Create a person: {}", person);

        if (repository.existsByName(person.name())) {
            log.warn("There was an attempt to create a Person with duplicated data. Payload provided: {}", person);
            throw new ConflictException("A person's name is unique. The system already have a person with the provided name");
        }

        final Person entity = mapper.from(person);
        return mapper.to(repository.save(entity));
    }

    /**
     * Fetch from the DB all the {@link Person}s that are not in the 'banned years'
     *
     * @implNote As the volume of data grows in the system a pagination such as {@link
     * org.springframework.data.domain.Slice} would be advisable
     */
    @Transactional(readOnly = true)
    public List<PersonFiltered> filterByBannedYears() {
        return repository.findByNotIn(bannedYears.bannedYearsFromFile());
    }

}