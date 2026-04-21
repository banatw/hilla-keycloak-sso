package com.example.application.security;

import static com.vaadin.flow.spring.security.VaadinSecurityConfigurer.vaadin;

import java.util.HashSet;
import java.util.Set;

import com.example.application.data.UserApp;
import com.example.application.data.UserAppRepository;
import com.vaadin.flow.spring.security.VaadinAwareSecurityContextHolderStrategyConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
// @Import(VaadinAwareSecurityContextHolderStrategyConfiguration.class)
public class SecurityConfiguration {
    @Autowired
    private UserAppRepository repository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain vaadinSecurityFilterChain(HttpSecurity http,ClientRegistrationRepository clientRegistrationRepository) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/images/*.png", "/*.css").permitAll());

        // Icons from the line-awesome addon
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/line-awesome/**").permitAll());
        
        http.with(vaadin(), vaadin -> {
            vaadin.oauth2LoginPage("/oauth2/authorization/keycloak");
            // var logoutHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
            // vaadin.logoutSuccessHandler(logoutHandler);
        });

        // http.with(vaadin(), vaadin -> {

        // });

        return http.build();
    }

    
}
