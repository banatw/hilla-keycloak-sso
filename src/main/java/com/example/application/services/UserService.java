package com.example.application.services;

import org.springframework.stereotype.Service;

import com.example.application.data.UserApp;
import com.example.application.data.UserAppRepository;

@Service
public class UserService {
    private final UserAppRepository userAppRepository;

    public UserService(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    public UserApp save(UserApp userApp) {
        return userAppRepository.save(userApp);
    }
}
