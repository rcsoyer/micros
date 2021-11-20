package org.acme.micros.webapp.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI config
 */
@Configuration
class OpenAPIConfig {

    @Bean
    OpenAPI openAPI(final BuildProperties buildProperties) {
        return new OpenAPI()
            .info(new Info()
                      .title("WebApp API")
                      .description("A web app microservice application")
                      .version(buildProperties.getVersion()));
    }
}
