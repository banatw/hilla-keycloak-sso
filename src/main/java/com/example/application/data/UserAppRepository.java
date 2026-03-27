package com.example.application.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;


public interface UserAppRepository extends JpaRepository<UserApp,Long>, JpaSpecificationExecutor<UserApp> {
    UserApp findByUsername(String username);
}
