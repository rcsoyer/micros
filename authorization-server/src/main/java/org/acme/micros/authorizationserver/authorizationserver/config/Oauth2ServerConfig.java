package org.acme.micros.authorizationserver.authorizationserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import static org.acme.micros.authorizationserver.authorizationserver.config.DefaultSecurityConfig.LOGIN_URL;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
class Oauth2ServerConfig {

    @Bean
    @Order(HIGHEST_PRECEDENCE)
    SecurityFilterChain authorizationServerSecurityFilterChain(final HttpSecurity securityBuilder)
      throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(securityBuilder);
        securityBuilder
          .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
          .oidc(withDefaults());
        return securityBuilder
                 .exceptionHandling(loginEntryPoint())
                 .oauth2ResourceServer(customizer -> customizer.jwt(withDefaults()))
                 .build();
    }

    @Bean
    WebSecurityCustomizer httpRequestsPermitAll() {
        return customizer ->
                 customizer
                   .ignoring()
                   .requestMatchers(GET,
                                    "/info",
                                    "/health",
                                    "/health/liveness",
                                    "/health/readiness");
    }

    @Bean
    AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    private Customizer<ExceptionHandlingConfigurer<HttpSecurity>> loginEntryPoint() {
        return exceptions -> exceptions
                               .defaultAuthenticationEntryPointFor(
                                 new LoginUrlAuthenticationEntryPoint(LOGIN_URL),
                                 new MediaTypeRequestMatcher(TEXT_HTML)
                               )
                               .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(LOGIN_URL));
    }
}
