package com.example.application;

import com.example.application.data.Role;
import com.example.application.data.UserApp;
import com.example.application.services.UserService;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.Set;

import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@StyleSheet(Lumo.STYLESHEET)
@StyleSheet(Lumo.UTILITY_STYLESHEET)
@Theme(value = "my-app")
public class Application implements AppShellConfigurator {
    //http://localhost:8081/realms/vaadin

    @Value("#{'${spring.security.oauth2.client.provider.keycloak.issuer-uri}'.split('//')[1].split('/')[2]}")
    private String keycloakRealm;

    @Autowired
    private UserService service;

    @Autowired
    private Keycloak keycloak;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return x->{
            UserApp admin = new UserApp();
            admin.setUsername("admin");
            admin.setName("Admin");
            admin.setRoles(Set.of(Role.ADMIN,Role.USER));
            service.save(admin);

            UserApp user = new UserApp();
            user.setUsername("user");
            user.setName("User");
            user.setRoles(Set.of(Role.USER));
            service.save(user);


        };
            
    }
}
