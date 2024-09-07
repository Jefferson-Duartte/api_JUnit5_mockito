package com.jefferson.api_junit_mockito.services.impl;

import com.jefferson.api_junit_mockito.domain.UserModel;
import com.jefferson.api_junit_mockito.domain.dtos.UserDTO;
import com.jefferson.api_junit_mockito.exceptions.DataIntegrityViolationException;
import com.jefferson.api_junit_mockito.exceptions.ObjectNotFoundException;
import com.jefferson.api_junit_mockito.repositories.UserRepository;
import com.jefferson.api_junit_mockito.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserModel findById(Long id) {
        Optional<UserModel> user = repository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado."));
    }

    public List<UserModel> findAll() {
        return repository.findAll();
    }

    public UserModel save(UserDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, UserModel.class));
    }

    private void findByEmail(UserDTO obj) {
        Optional<UserModel> user = repository.findByEmail(obj.getEmail());
        if (user.isPresent()) throw new DataIntegrityViolationException("Email já cadastrado no sistema.");
    }

}
