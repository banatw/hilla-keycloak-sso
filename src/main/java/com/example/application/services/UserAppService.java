package com.example.application.services;


import com.example.application.data.UserApp;
import com.example.application.data.UserAppRepository;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.crud.ListRepositoryService;

import jakarta.annotation.security.RolesAllowed;

@BrowserCallable
@RolesAllowed("ROLE_ADMIN")
public class UserAppService extends ListRepositoryService<UserApp,Long,UserAppRepository> {
   
    public UserApp findByUsername(String username) {
        return this.getRepository().findByUsername(username);
    }
    
    public UserApp newUserApp() {
        return new UserApp();
    }

    public UserApp saveUserApp(UserApp userApp) {
        return this.getRepository().save(userApp);
    }

    public void delete(UserApp userApp) {
        this.getRepository().delete(userApp);
    }
}
