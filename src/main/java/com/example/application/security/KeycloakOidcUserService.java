package com.example.application.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import com.example.application.data.UserAppRepository;

@Component
public class KeycloakOidcUserService extends OidcUserService {
    private final UserAppRepository userAppRepository;


    public KeycloakOidcUserService(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }


    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        // TODO Auto-generated method stub
        OidcUser oidcUser = super.loadUser(userRequest);

        Set<GrantedAuthority> mappedAuthorities = new HashSet<>(oidcUser.getAuthorities());
        
        userAppRepository.findByUsername(oidcUser.getPreferredUsername()).getRoles().stream()
        .forEach(role -> {
            String roleName = String.valueOf(role.name());
            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
        });

        return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
    }
    
}