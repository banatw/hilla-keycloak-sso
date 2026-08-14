package com.example.application.services;

import java.util.Optional;

import com.example.application.data.User;
import com.example.application.data.UserRepository;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.crud.ListRepositoryService;

import jakarta.annotation.security.RolesAllowed;

@BrowserCallable
@RolesAllowed({ "ADMIN" })
public class UserRoleService extends ListRepositoryService<User, Long, UserRepository> {
    /**
     *
     */
    private final UserService service;

    public UserRoleService(UserService svc) {
        this.service = svc;
    }

    public User save(User entity) {
        return service.save(entity);
    }

    public Optional<User> addUser() {
        return Optional.of(new User());
    }

    public void delete(User user) {
        service.delete(user.getId());
    }
}
