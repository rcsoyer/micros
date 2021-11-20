package org.acme.micros.webapp.web;

import org.acme.micros.webapp.BaseTestContainer;
import org.acme.micros.webapp.WebApp;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("inttest")
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = WebApp.class)
abstract class BaseControllerTest extends BaseTestContainer {
}
