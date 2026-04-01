package com.example.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;

import com.example.application.data.UserAppRepository;
import com.vaadin.hilla.BrowserCallable;

import jakarta.annotation.security.RolesAllowed;

@BrowserCallable
@RolesAllowed("ADMIN")
public class KeycloakService {
    @Value("#{'${spring.security.oauth2.client.provider.keycloak.issuer-uri}'.split('//')[1].split('/')[2]}")
    private String keycloakRealm;

    private final Keycloak keycloak;

    public record KeycloakUserRecord(String value,String label) {
    }

    public KeycloakService(Keycloak keycloak, UserAppRepository r) {
        this.keycloak = keycloak;
    }

    // public List<KeycloakUserRecord> getKeycloakUsers() {
    //     return keycloak.realm("vaadin").users().list().stream().map(data -> new KeycloakUserRecord(data.getUsername(), data.getUsername())).collect(Collectors.toList());
    // }

    public KeycloakUserRecord getKeycloakUserRecord(String username) {
        return new KeycloakUserRecord(username, username);
    }

     public List<String> getKeycloakUsers() {
        return keycloak.realm("vaadin").users().list().stream().map(UserRepresentation::getUsername).collect(Collectors.toList());
    }
}
