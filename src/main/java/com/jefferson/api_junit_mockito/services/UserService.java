package com.jefferson.api_junit_mockito.services;

import com.jefferson.api_junit_mockito.domain.UserModel;
import com.jefferson.api_junit_mockito.domain.dtos.UserDTO;

import java.util.List;

public interface UserService {
    UserModel findById(Long id);

    List<UserModel> findAll();

    UserModel save(UserDTO obj);

    void delete(Long id);

}
