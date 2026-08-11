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
import org.springframework.stereotype.Service;

import com.example.application.data.UserRepository;

@Service
public class MyOidcUserService extends OidcUserService {
    private final UserRepository userRepository;

    public MyOidcUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        // TODO Auto-generated method stub
        OidcUser oidcUser = super.loadUser(userRequest);
        String username = oidcUser.getPreferredUsername();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        // grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        userRepository.findByUsername(username).ifPresentOrElse(user -> {
            user.getRoles().stream().forEach(role -> {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
            });
        }, () -> {
        });
        return new DefaultOidcUser(grantedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
    }

}
