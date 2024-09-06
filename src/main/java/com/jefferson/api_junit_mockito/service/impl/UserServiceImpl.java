package com.jefferson.api_junit_mockito.service.impl;

import com.jefferson.api_junit_mockito.domain.UserModel;
import com.jefferson.api_junit_mockito.exception.ObjectNotFoundException;
import com.jefferson.api_junit_mockito.repository.UserRepository;
import com.jefferson.api_junit_mockito.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserModel findById(Long id) {
        Optional<UserModel> user = repository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado."));
    }
}
