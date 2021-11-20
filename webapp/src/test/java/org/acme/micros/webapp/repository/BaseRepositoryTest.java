package org.acme.micros.webapp.repository;

import org.acme.micros.webapp.BaseTestContainer;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@ActiveProfiles("inttest")
@AutoConfigureTestDatabase(replace = NONE)
abstract class BaseRepositoryTest extends BaseTestContainer {
}
