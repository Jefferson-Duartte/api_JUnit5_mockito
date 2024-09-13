package com.jefferson.api_junit_mockito.services.impl;

import com.jefferson.api_junit_mockito.domain.UserModel;
import com.jefferson.api_junit_mockito.domain.dtos.UserDTO;
import com.jefferson.api_junit_mockito.exceptions.DataIntegrityViolationException;
import com.jefferson.api_junit_mockito.exceptions.ObjectNotFoundException;
import com.jefferson.api_junit_mockito.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private UserModel user;

    @Mock
    private UserDTO userDTO;

    @Mock
    private ModelMapper mapper;

    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    @Captor
    private ArgumentCaptor<UserModel> userModelArgumentCaptor;

    @BeforeEach
    void setup() {
        createUsers();
    }

    void createUsers() {
        user = new UserModel(1L, "Jefferson", "jefferson@mail.com", "1234");
        userDTO = new UserDTO(1L, "Jefferson", "jefferson@mail.com", "1234");
    }

    @Nested
    class FindById {

        public static final String OBJECT_NOT_FOUND_MESSAGE = "Objeto não encontrado.";

        @Test
        @DisplayName("Should get user by id with success")
        void shouldGetUserByIdWithSuccess() {

            //Arrange
            doReturn(Optional.of(user)).when(repository).findById(longArgumentCaptor.capture());

            //Act
            var output = service.findById(user.getId());

            //Assert
            assertThat(longArgumentCaptor.getValue()).isEqualTo(user.getId());
            assertThat(output)
                    .isNotNull()
                    .isInstanceOf(UserModel.class)
                    .extracting(UserModel::getName, UserModel::getEmail)
                    .containsExactly(user.getName(), user.getEmail());

            verify(repository, times(1)).findById(user.getId());
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
    class FindAll {

        @Test
        @DisplayName("Should return all users with success")
        void shouldReturnAllUserWithSuccess() {

            //Arrange
            doReturn(List.of(user)).when(repository).findAll();

            //Act
            var output = service.findAll();

            //Assert
            assertThat(output)
                    .isNotNull()
                    .hasSize(1)
                    .first()
                    .isInstanceOf(UserModel.class)
                    .extracting(UserModel::getId, UserModel::getName, UserModel::getEmail, UserModel::getPassword)
                    .containsExactly(user.getId(), user.getName(), user.getEmail(), user.getPassword());

            verify(repository, times(1)).findAll();
        }
    }

    @Nested
    class Create {

        @Test
        @DisplayName("Should create an user with success")
        void shouldCreateAnUserWithSuccess() {

            //Arrange
            doReturn(user).when(repository).save(any());

            //Act
            var output = service.save(userDTO);

            //Assert
            assertThat(output)
                    .isNotNull()
                    .isInstanceOf(UserModel.class)
                    .extracting(UserModel::getName, UserModel::getEmail)
                    .containsExactly(user.getName(), user.getEmail());
        }

        @Test
        @DisplayName("Should throw DataIntegrityViolationException when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {

            //Arrange
            doThrow(new DataIntegrityViolationException("Email já cadastrado no sistema.")).when(repository).save(any());

            //Act & Assert
            assertThatThrownBy(() -> service.save(userDTO))
                    .isInstanceOf(DataIntegrityViolationException.class)
                    .hasMessageContaining("Email já cadastrado no sistema.");

        }
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}