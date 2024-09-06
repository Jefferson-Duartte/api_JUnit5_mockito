package com.jefferson.api_junit_mockito.services;

import com.jefferson.api_junit_mockito.domain.UserModel;

public interface UserService {
    UserModel findById(Long id);
}
