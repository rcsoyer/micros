package org.acme.micros.webapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration for auditing JPA entities
 */
@Configuration
@EnableJpaAuditing
public class AuditConfig {

/*    *
     The user that is logged in the app when creating or modifying resources
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                             .map(Authentication::getName);
    }*/

}
