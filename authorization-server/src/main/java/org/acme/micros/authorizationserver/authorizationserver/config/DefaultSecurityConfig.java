package org.acme.micros.authorizationserver.authorizationserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
class DefaultSecurityConfig {

    static final String LOGIN_URL = "/login";

    @Bean
    SecurityFilterChain loginFilterChain(final HttpSecurity securityBuilder)
      throws Exception {
        return securityBuilder
                 .sessionManagement(sessionCustomizer())
                 .oauth2Login(oauth2LoginCustomizer())
                 .build();
    }

    private Customizer<SessionManagementConfigurer<HttpSecurity>> sessionCustomizer() {
        return sessionCustom -> sessionCustom.maximumSessions(2)
                                             .expiredUrl(LOGIN_URL);
    }

    private Customizer<OAuth2LoginConfigurer<HttpSecurity>> oauth2LoginCustomizer() {
        return oauth2Login -> oauth2Login
                                .userInfoEndpoint(customizer -> customizer.oidcUserService(new OidcUserService()));
    }

}