package com.jefferson.api_junit_mockito.services.impl;

import com.jefferson.api_junit_mockito.domain.UserModel;
import com.jefferson.api_junit_mockito.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    @Nested
    class findById {

        @Test
        @DisplayName("Should get user by id with success")
        void shouldGetUserByIdWithSuccess() {

            //Arrange
            var user = new UserModel(1L, "Jefferson", "jeff@mail.com", "123");

            doReturn(Optional.of(user)).when(repository).findById(longArgumentCaptor.capture());

            //Act
            var output = service.findById(user.getId());

            //Assert
            assertNotNull(output);
            assertEquals(UserModel.class, output.getClass());
            assertEquals(user.getId(), longArgumentCaptor.getValue());
            assertEquals(user.getName(), output.getName());
            assertEquals(user.getEmail(), output.getEmail());


        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOcurred() {

            //Arrange
            doThrow(RuntimeException.class).when(repository).findById(any());

            //Act & Assert
            assertThrows(RuntimeException.class, () -> service.findById(1L));

        }

    }


    @Test
    void findAll() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}