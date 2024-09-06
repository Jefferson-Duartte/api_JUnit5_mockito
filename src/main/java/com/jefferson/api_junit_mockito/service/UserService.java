package com.jefferson.api_junit_mockito.service;

import com.jefferson.api_junit_mockito.domain.UserModel;

public interface UserService {
    UserModel findById(Long id);
}
