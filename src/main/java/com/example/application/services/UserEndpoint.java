package com.example.application.services;

import com.example.application.data.User;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.util.UriComponentsBuilder;

@BrowserCallable
@AnonymousAllowed
public class UserEndpoint {

    

    public Optional<User> getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof OidcUser oidcUser) {
            Collection<String> roles = context.getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            User user = new User(oidcUser,roles);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    //  public String getLogoutUrl() {
    //     SecurityContext context = SecurityContextHolder.getContext();
    //     Object principal = context.getAuthentication().getPrincipal();
    //     if (principal instanceof OidcUser user) {
    //         return UriComponentsBuilder
    //                 .fromUriString(user.getIssuer() + "/protocol/openid-connect/logout")
    //                 .queryParam("id_token_hint", user.getIdToken().getTokenValue())
    //                 .queryParam("post_logout_redirect_uri", "http://localhost:8082").toUriString();
    //     }
    //     return "/logout";
    // }

    // public String getLogoutUrl() {
    //     return UriComponentsBuilder.fromUriString("localhost:8082").toUriString();
    // }
}
