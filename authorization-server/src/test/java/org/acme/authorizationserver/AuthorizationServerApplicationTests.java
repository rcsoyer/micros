package org.acme.authorizationserver;

import org.acme.micros.authorizationserver.authorizationserver.AuthorizationServerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = AuthorizationServerApplication.class)
class AuthorizationServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
