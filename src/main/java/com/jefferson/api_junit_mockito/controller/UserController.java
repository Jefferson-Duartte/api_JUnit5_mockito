package com.jefferson.api_junit_mockito.controller;

import com.jefferson.api_junit_mockito.domain.UserModel;
import com.jefferson.api_junit_mockito.domain.dto.UserDTO;
import com.jefferson.api_junit_mockito.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDTO.class));
    }

}
