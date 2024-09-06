package com.jefferson.api_junit_mockito.services.impl;

import com.jefferson.api_junit_mockito.domain.User;
import com.jefferson.api_junit_mockito.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User findUserById(Long id) {
        return null;
    }
}
