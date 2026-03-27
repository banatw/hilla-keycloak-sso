package com.example.application.data;

import java.util.Collection;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
public class User {
    @Nonnull
    private String username;

    @Nonnull
    private String firstname;

    @Nonnull
    private String lastname;

    @Nonnull
    private String email;

    @Nonnull
    private Collection<String> roles;

    public User(OidcUser oidcUser, Collection<String> roles) {
        this.username = oidcUser.getPreferredUsername();
        this.firstname =oidcUser.getGivenName();
        this.lastname = oidcUser.getFamilyName();
        this.email = oidcUser.getEmail();
        this.roles = roles;
    }
}
