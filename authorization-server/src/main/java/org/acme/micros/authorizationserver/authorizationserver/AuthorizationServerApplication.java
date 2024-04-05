package org.acme.micros.authorizationserver.authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorizationServerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

}
