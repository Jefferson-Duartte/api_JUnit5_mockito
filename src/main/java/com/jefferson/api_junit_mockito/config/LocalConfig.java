package com.jefferson.api_junit_mockito.config;

import com.jefferson.api_junit_mockito.domain.UserModel;
import com.jefferson.api_junit_mockito.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    private final UserRepository repository;

    public LocalConfig(UserRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void startDB(){

        UserModel u1 = new UserModel(1L, "Jeferson", "jeff@gmail.com" , "123");
        UserModel u2 = new UserModel(2L, "Jeferson2", "jeff2@gmail.com" , "123");

        repository.saveAll(List.of(u1, u2));

    }
}
