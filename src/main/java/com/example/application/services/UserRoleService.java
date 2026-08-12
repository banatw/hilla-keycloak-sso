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
    private final UserRepository repository;

    public UserRoleService(UserRepository repo) {
        this.repository = repo;
    }

    public User save(User entity) {
        return repository.save(entity);
    }

    public Optional<User> addUser() {
        return Optional.of(new User());
    }
}
