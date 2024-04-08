package org.acme.authorizationserver.fixture;

import java.time.Clock;
import java.util.List;
import java.util.Map;

import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.JWT_BEARER;
import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.ACCESS_TOKEN;
import static org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat.REFERENCE;

@NoArgsConstructor(access = PRIVATE)
public final class TokenUtils {

    private static final TokenGenerator TOKEN_GENERATOR = new TokenGenerator();

    public static OAuth2AccessToken generateAccessToken(final Authentication user) {
        return TOKEN_GENERATOR.generate(user);
    }

    private static class TokenGenerator {

        private final OAuth2AccessTokenGenerator tokenGenerator;
        private final DefaultOAuth2TokenContext.Builder tokenContext;

        private TokenGenerator() {
            this.tokenGenerator = new OAuth2AccessTokenGenerator();
            final var tokenSettings = TokenSettings
                                        .builder()
                                        .accessTokenFormat(REFERENCE)
                                        .build();
            final var registeredClient = RegisteredClient
                                           .withId("default")
                                           .tokenSettings(tokenSettings)
                                           .clientId("default")
                                           .authorizationGrantType(JWT_BEARER)
                                           .build();
            this.tokenContext = DefaultOAuth2TokenContext
                                  .builder()
                                  .authorizationGrantType(JWT_BEARER)
                                  .tokenType(ACCESS_TOKEN)
                                  .registeredClient(registeredClient);
        }

        private OAuth2AccessToken generate(final Authentication user) {
            return tokenGenerator.generate(tokenContext.principal(user).build());
        }
    }

    public static class AuthenticationUtils {

        private static final String DEFAULT_SUB = "dummy";
        private static final String JWT_KEY = "sub";

        public static OAuth2AuthenticationToken oauth2Authenticated() {
            final var oidcUser = oidcUser(oauth2AuthenticationToken());
            final var accessToken = new OAuth2AuthenticationToken(oidcUser, List.of(), DEFAULT_SUB);
            accessToken.setAuthenticated(true);
            return accessToken;
        }

        private static Authentication oauth2AuthenticationToken() {
            final var oAuth2User = new DefaultOAuth2User(List.of(), Map.of(JWT_KEY, DEFAULT_SUB), JWT_KEY);
            return new OAuth2AuthenticationToken(oAuth2User, List.of(), DEFAULT_SUB);
        }

        private static OidcUser oidcUser(final Authentication oauth2User) {
            final String oidcToken = TokenUtils.generateAccessToken(oauth2User).getTokenValue();
            final Map<String, Object> claims = Map.of(JWT_KEY, DEFAULT_SUB);
            return new DefaultOidcUser(List.of(),
                                       new OidcIdToken(oidcToken,
                                                       Clock.systemUTC().instant(),
                                                       Clock.systemUTC().instant(),
                                                       claims));
        }
    }

}
