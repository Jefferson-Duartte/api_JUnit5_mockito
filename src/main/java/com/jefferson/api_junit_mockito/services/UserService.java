package com.jefferson.api_junit_mockito.services;

import com.jefferson.api_junit_mockito.domain.User;

public interface UserService {
    User findUserById(Long id);
}
