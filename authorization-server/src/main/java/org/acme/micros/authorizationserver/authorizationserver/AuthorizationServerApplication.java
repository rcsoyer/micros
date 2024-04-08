package org.acme.micros.authorizationserver.authorizationserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AuthorizationServerApplication implements InitializingBean {

    public static void main(final String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

    @Override
    public void afterPropertiesSet() {
        log.info("Number of CPU cores the JVM can see: {}", Runtime.getRuntime().availableProcessors());
    }
}
