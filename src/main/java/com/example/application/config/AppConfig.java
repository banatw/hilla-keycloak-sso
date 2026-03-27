package com.example.application.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // http://localhost:8081/realms/vaadin

    @Value("#{'${spring.security.oauth2.client.provider.keycloak.issuer-uri}'.split('//')[1].split('/')[0]}")
    private String keycloakServerUrl;

    @Value("#{'${spring.security.oauth2.client.provider.keycloak.issuer-uri}'.split('//')[1].split('/')[2]}")
    private String keycloakRealm;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String keycloakClientSecret;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String keycloakClientId;

    @Bean
    Keycloak getKeycloak() {
        Keycloak keycloak = KeycloakBuilder.builder()
                            .serverUrl("http://" + keycloakServerUrl)
                            .realm("vaadin")
                            .clientId("hilla")
                            .clientSecret("0X4xRC6Lc8izC6j8kZ9zPT95OIwtAmYB")
                            .grantType(OAuth2Constants.CLIENT_CREDENTIALS).build();
        return keycloak;
    }
}
