package org.acme.micros.webapp.configuration;

import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
class JacksonConfig {

    @Bean
    Module constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }
}
