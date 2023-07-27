package org.acme.micros.webapp.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.acme.micros.webapp.domain.Person;
import org.acme.micros.webapp.repository.PersonRepository;
import org.acme.micros.webapp.service.BannedYearsDefinition;
import org.acme.micros.webapp.service.PersonService;
import org.acme.micros.webapp.service.dto.PersonCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.web.servlet.MockMvc;

import static java.time.Month.DECEMBER;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static java.time.Month.MARCH;
import static java.time.Month.OCTOBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PersonControllerTestIT extends BaseControllerTest {

    private static final String BASE_PATH = "/persons";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private BannedYearsDefinition bannedYearsDefinition;

    @Nested
    @DisplayName("Organize the tests related to the endpoint POST /persons together in a single class")
    class CreatePerson {

        @Test
        void createPerson_wheyPayloadOkThenSuccess() throws Exception {
            final var person = new PersonCreateRequest("j.r.r tolkien", LocalDate.now());
            final var json = objectMapper.writeValueAsString(person);

            mockMvc.perform(post(BASE_PATH)
                              .contentType(APPLICATION_JSON)
                              .content(json))
                   .andExpect(status().isCreated())
                   .andExpect(jsonPath("$.id").exists())
                   .andExpect(jsonPath("$.name").value(person.name()))
                   .andExpect(jsonPath("$.birthDate").value(person.birthDate().toString()));

            final var example = Example.of(new Person(person.name(), person.birthDate()));
            final Optional<Person> found = personRepository.findOne(example);

            assertTrue(found.isPresent());
            assertEquals(person.name(), found.get().getName());
            assertEquals(person.birthDate(), found.get().getBirthDate());
        }

        @Test
        void createPerson_wheyPayloadMissingMandatoryThenError() throws Exception {
            final var json = objectMapper.writeValueAsString(new PersonCreateRequest(null, null));

            mockMvc.perform(post(BASE_PATH)
                              .contentType(APPLICATION_JSON)
                              .content(json))
                   .andExpect(status().isBadRequest())
                   .andExpect(jsonPath("$.title").value("Constraint Violation"))
                   .andExpect(jsonPath("$.violations", hasSize(2)))
                   .andExpect(jsonPath("$.violations[0].field").value("birthDate"))
                   .andExpect(jsonPath("$.violations[0].message").value("Person's birthdate is mandatory"))
                   .andExpect(jsonPath("$.violations[1].field").value("name"))
                   .andExpect(jsonPath("$.violations[1].message").value("Person's name is mandatory"));

            assertThat(personRepository.findAll())
              .isEmpty();
        }

        @Test
        void createPerson_wheyPayloadInvalidThenError() throws Exception {
            final var shortName = "R";
            final var futureBirthDate = LocalDate.now().plusDays(1L);
            final var json = objectMapper.writeValueAsString(new PersonCreateRequest(shortName, futureBirthDate));

            mockMvc.perform(post(BASE_PATH)
                              .contentType(APPLICATION_JSON)
                              .content(json))
                   .andExpect(status().isBadRequest())
                   .andExpect(jsonPath("$.title").value("Constraint Violation"))
                   .andExpect(jsonPath("$.violations", hasSize(2)))
                   .andExpect(jsonPath("$.violations[0].field").value("birthDate"))
                   .andExpect(jsonPath("$.violations[0].message").value("Person's birthdate cannot be in the future"))
                   .andExpect(jsonPath("$.violations[1].field").value("name"))
                   .andExpect(jsonPath("$.violations[1].message").value("Person's name must have at least 2 characters and a maximum of 150"));

            assertThat(personRepository.findAll())
              .isEmpty();
        }

        @Test
        void createPerson_wheyPersonExistsThenError() throws Exception {
            final var person = new PersonCreateRequest("j.r.r tolkien", LocalDate.now());
            personService.create(person);

            final var json = objectMapper.writeValueAsString(person);

            mockMvc.perform(post(BASE_PATH)
                              .contentType(APPLICATION_JSON)
                              .content(json))
                   .andExpect(status().isConflict())
                   .andExpect(jsonPath("$.title").value("Conflict"))
                   .andExpect(jsonPath("$.detail").value("A person's name is unique. The system already have a person with the provided name"));

            final var example = Example.of(new Person(person.name(), null));
            assertThat(personRepository.count(example))
              .isEqualTo(1L);
        }
    }

    @Nested
    class FindPeopleNotBanned {

        private List<Integer> bannedYears;

        @BeforeEach
        @DisplayName("set up data for the test")
        void setUp() {
            bannedYears = new ArrayList<>(bannedYearsDefinition.bannedYearsFromFile());

            final var person1Banned = new PersonCreateRequest("j.r.r tolkien",
                                                              LocalDate.of(bannedYears.get(0), JANUARY, 1));
            personService.create(person1Banned);

            final var person2Banned = new PersonCreateRequest("Stephen King",
                                                              LocalDate.of(bannedYears.get(1), FEBRUARY, 2));
            personService.create(person2Banned);

            final var person3Banned = new PersonCreateRequest("Isaac Asimov",
                                                              LocalDate.of(bannedYears.get(2), MARCH, 3));
            personService.create(person3Banned);

            final var person4NotBanned = new PersonCreateRequest("Sylvia Plath",
                                                                 LocalDate.of(1932, OCTOBER, 27));
            personService.create(person4NotBanned);

            final var person5NotBanned = new PersonCreateRequest("Ada Lovelace",
                                                                 LocalDate.of(1815, DECEMBER, 10));
            personService.create(person5NotBanned);
        }

        @Test
        void findPeopleNotBanned() throws Exception {
            final var resultJson = mockMvc.perform(get(BASE_PATH)
                                                     .contentType(APPLICATION_JSON))
                                          .andExpect(status().isOk())
                                          .andReturn()
                                          .getResponse()
                                          .getContentAsString();

            final List<String> birthDates = JsonPath.read(resultJson, "$[*].birthDate");
            final List<Integer> yearsResult = birthDates.stream().map(LocalDate::parse).map(LocalDate::getYear).toList();

            assertThat(yearsResult)
              .hasSize(2)
              .doesNotContainAnyElementsOf(bannedYears);
        }
    }
}