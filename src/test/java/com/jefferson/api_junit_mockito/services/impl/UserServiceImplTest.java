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
    private Optional<UserModel> optionalUser;

    @Mock
    private ModelMapper mapper;

    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    public final String OBJECT_NOT_FOUND_MESSAGE = "Objeto não encontrado.";

    @BeforeEach
    void setup() {
        createUsers();
    }

    void createUsers() {
        user = new UserModel(1L, "Jefferson", "jefferson@mail.com", "1234");
        userDTO = new UserDTO(1L, "Jefferson", "jefferson@mail.com", "1234");
        optionalUser = Optional.of(user);
    }

    @Nested
    class FindById {


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
        @DisplayName("Should throw DataIntegrityViolationException when email is already in use")
        void shouldThrowExceptionWhenEmailIsAlreadyInUse() {

            //Arrange
            doReturn(optionalUser).when(repository).findByEmail(userDTO.getEmail());

            //Act & Assert
            assertThatThrownBy(() -> service.findByEmail(userDTO))
                    .isInstanceOf(DataIntegrityViolationException.class)
                    .hasMessageContaining("Email já cadastrado no sistema.");

        }
    }

    @Nested
    class Update{

        @Test
        @DisplayName("Should update an user with success")
        void shouldUpdateAnUserWithSuccess(){

            //Arrange
            doReturn(user).when(mapper).map(userDTO, UserModel.class);
            doReturn(optionalUser).when(repository).findById(user.getId());
            doReturn(user).when(repository).save(user);

            //Act
            var output = service.update(userDTO);

            //Assert
            assertThat(output)
                    .isNotNull()
                    .isInstanceOf(UserModel.class)
                    .extracting(UserModel::getId, UserModel::getEmail)
                    .containsExactly(user.getId(), user.getEmail());

        }

        @Test
        @DisplayName("Should throw DataIntegrityViolationException when email is already in use in update method")
        void shouldThrowExceptionWhenEmailIsAlreadyInUseInUpdateMethod() {

            //Arrange
            doReturn(optionalUser).when(repository).findByEmail(userDTO.getEmail());

            //Act & Assert
            assertThatThrownBy(() -> service.findByEmail(userDTO))
                    .isInstanceOf(DataIntegrityViolationException.class)
                    .hasMessageContaining("Email já cadastrado no sistema.");

        }

    }

    @Nested
    class Delete{

        @Test
        @DisplayName("Should delete user with success")
        void shouldDeleteUserWithSuccess(){

            //Arrange
            doReturn(optionalUser).when(repository).findById(anyLong());
            doNothing().when(repository).deleteById(anyLong());

            //Act
            service.delete(user.getId());

            //Assert
            verify(repository, times(1)).deleteById(anyLong());

        }

        @Test
        @DisplayName("Should throw ObjectNotFoundException when user is not found in delete method")
        void shouldThrowObjectNotFoundExceptionWhenUserNotFound() {

            //Arrange
            doThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND_MESSAGE)).when(repository).findById(anyLong());
            //Act & Assert
            assertThatThrownBy(() -> service.delete(212L))
                    .isInstanceOf(ObjectNotFoundException.class)
                    .hasMessageContaining(OBJECT_NOT_FOUND_MESSAGE);
        }

    }
}