package com.example.application.services;

import com.example.application.data.User;
import com.example.application.data.UserRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import java.util.Optional;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@BrowserCallable
@AnonymousAllowed
public class UserEndpoint {
    private final UserRepository userRepository;

    public UserEndpoint(UserRepository repo) {
        this.userRepository = repo;
    }

    public Optional<User> getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof OidcUser oidcUser) {
            String username = oidcUser.getPreferredUsername();
            return userRepository.findByUsername(username);
        }
        return Optional.empty();
    }
}
