package com.jefferson.api_junit_mockito.controller;

import com.jefferson.api_junit_mockito.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){

        User user = new User(id, "Jefferson", "jeff@gmail.com", "jeff123" );

        return ResponseEntity.ok().body(user);
    }

}
