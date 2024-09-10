package com.jefferson.api_junit_mockito.services.impl;

import com.jefferson.api_junit_mockito.domain.UserModel;
import com.jefferson.api_junit_mockito.exceptions.ObjectNotFoundException;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    private UserModel createUser() {
        return new UserModel(1L, "Jefferson", "jefferson@mail.com", "1234");
    }

    @Nested
    class findById {

        public static final String OBJECT_NOT_FOUND_MESSAGE = "Objeto não encontrado.";

        @Test
        @DisplayName("Should get user by id with success")
        void shouldGetUserByIdWithSuccess() {

            //Arrange
            var user = createUser();

            doReturn(Optional.of(user)).when(repository).findById(longArgumentCaptor.capture());

            //Act
            var output = service.findById(user.getId());

            //Assert
            assertThat(longArgumentCaptor.getValue()).isEqualTo(user.getId());
            assertThat(output).isNotNull();
            assertThat(output.getClass()).isEqualTo(UserModel.class);
            assertThat(user.getName()).isEqualTo(output.getName());
            assertThat(user.getEmail()).isEqualTo(output.getEmail());

        }

        @Test
        @DisplayName("Should throw ObjectNotFoundException when user is not found")
        void shouldThrowObjectNotFoundExceptionWhenUserNotFound() {

            //Arrange
            doThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND_MESSAGE)).when(repository).findById(any());

            //Act & Assert
            assertThatThrownBy(() -> service.findById(212L))
                    .isInstanceOf(ObjectNotFoundException.class)
                    .hasMessageContaining(OBJECT_NOT_FOUND_MESSAGE);

        }

    }


    @Nested
    class findAll {

        @Test
        @DisplayName("Should return all users with success")
        void shouldReturnAllUserWithSuccess() {

            //Arrange
            var user = createUser();

            doReturn(List.of(user)).when(repository).findAll();
            //Act

            var output = service.findAll();

            //Assert

            assertThat(output)
                    .isNotNull()
                    .hasSize(1);

            assertThat(UserModel.class).isEqualTo(output.getFirst().getClass());
            assertThat(output.get(0))
                    .extracting(UserModel::getId, UserModel::getName, UserModel::getEmail, UserModel::getPassword)
                    .containsExactly(user.getId(), user.getName(), user.getEmail(), user.getPassword());

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