package com.jefferson.api_junit_mockito.config;

import com.jefferson.api_junit_mockito.domain.UserModel;
import com.jefferson.api_junit_mockito.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @PostConstruct
    public void startDB(){

        UserModel u1 = new UserModel(1L, "Jeferson", "jeff@gmail.com" , "123");
        UserModel u2 = new UserModel(2L, "Jeferson2", "jeff2@gmail.com" , "123");

        repository.saveAll(List.of(u1, u2));

    }
}
