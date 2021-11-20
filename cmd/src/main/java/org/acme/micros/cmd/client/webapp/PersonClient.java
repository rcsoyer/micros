package org.acme.micros.cmd.client.webapp;

import java.util.List;

import org.acme.micros.cmd.runner.command.dto.PersonCreateDto;
import org.acme.micros.cmd.runner.command.dto.PersonNotBannedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * API client to integrate with the endpoints '/persons' on the microservice 'webapp'
 */
@FeignClient(name = "${services.webapp.name}", contextId = "personClient", path = "${services.webapp.endpoints.persons}",
             fallbackFactory = PersonClient.PersonClientFallbackFactory.class)
public interface PersonClient {

    @PostMapping
    void createPerson(@RequestBody PersonCreateDto person);

    @GetMapping
    List<PersonNotBannedResponse> findPeopleNotBanned();

    @Slf4j
    @Component
    class PersonClientFallbackFactory implements FallbackFactory<PersonClient> {

        @Override
        public PersonClient create(final Throwable cause) {
            return new PersonClient() {
                @Override
                public void createPerson(final PersonCreateDto person) {
                    log.error("The person could not be created. payload={}", person);
                    log.error("The microservice cannot respond at this moment", cause);
                }

                @Override
                public List<PersonNotBannedResponse> findPeopleNotBanned() {
                    log.error("The microservice cannot respond at this moment", cause);
                    return List.of();
                }
            };
        }
    }
}
