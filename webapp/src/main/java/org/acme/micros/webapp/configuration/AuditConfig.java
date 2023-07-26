package org.acme.micros.webapp.configuration;

//its useful for reference. But in here disabling it since it would demand to configure a properly secure access
/**
 * Configuration for auditing JPA entities
 */
/*@Configuration
@EnableJpaAuditing
public class AuditConfig {

    *//***
     * The user that is logged in the app when creating or modifying resources
     *//*
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional
                       .ofNullable(SecurityContextHolder.getContext().getAuthentication())
                       .map(Authentication::getName);
    }

}*/
