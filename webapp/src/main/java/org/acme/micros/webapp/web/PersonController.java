package org.acme.micros.webapp.web;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.micros.webapp.repository.projection.PersonFiltered;
import org.acme.micros.webapp.service.PersonService;
import org.acme.micros.webapp.service.dto.PersonCreateRequest;
import org.acme.micros.webapp.service.dto.PersonCreateResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("persons")
class PersonController {

    private final PersonService service;

    @PostMapping
    @ResponseStatus(CREATED)
    PersonCreateResponse createPerson(@RequestBody @Valid final PersonCreateRequest createCommand) {
        log.debug("Rest API call to create a person={}", createCommand);
        return service.create(createCommand);
    }

    @GetMapping
    List<PersonFiltered> findPeopleNotBanned() {
        log.debug("Rest API call to get all the people not born in the banned years");
        return service.filterByBannedYears();
    }
}
