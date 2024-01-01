package com.jm.jamesapp.services;

import com.jm.jamesapp.models.dto.SaveUserDto;
import com.jm.jamesapp.models.dto.UpdateUserDto;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.user.enums.UserRole;
import com.jm.jamesapp.repositories.UserRepository;
import com.jm.jamesapp.services.exceptions.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserModel userAdmin;
    private UserModel userUser;
    private SaveUserDto saveUserDtoUSER;
    private SaveUserDto saveUserDtoADMIN;
    private UpdateUserDto updateUserDto;


    @BeforeEach
    public void setUp() {
        userAdmin = new UserModel("James App", "jamesapp", "james@jamesapp.com.br", "12345678", UserRole.ADMIN);
        userUser = new UserModel("Felipe Adriano", "lipezin", "felipe.adriano@jamesapp.com.br", "lipe@54321", UserRole.USER);

        saveUserDtoUSER = new SaveUserDto();
        saveUserDtoUSER.setName("Felipe Adriano");
        saveUserDtoUSER.setUsername("lipezin1");
        saveUserDtoUSER.setPassword("12345678");
        saveUserDtoUSER.setEmail("felipe.adriano@jamesapp.com.br");
        saveUserDtoUSER.setRole(UserRole.USER);

        saveUserDtoADMIN = new SaveUserDto();
        saveUserDtoADMIN.setName("James App");
        saveUserDtoADMIN.setUsername("jamesapp");
        saveUserDtoADMIN.setPassword("12345678");
        saveUserDtoADMIN.setEmail("james@jamesapp.com.br");
        saveUserDtoADMIN.setRole(UserRole.ADMIN);

        updateUserDto = new UpdateUserDto();
        updateUserDto.setName("Felipe Adriano");
        updateUserDto.setUsername("prestez");
        updateUserDto.setEmail("felipe.adriano@jamesapp.com.br");
    }

    @Test
    @DisplayName("Should create a normal user successfully")
    public void testCreateUser() {
        when(userRepository.save(Mockito.any(UserModel.class))).thenReturn(userUser);

        UserModel savedUser = userService.save(saveUserDtoUSER, null);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getRole()).isEqualTo(UserRole.USER);
    }

    @Test
    @DisplayName("Should create a admin user successfully")
    public void testCreateUserAdmin() {
        when(userRepository.save(Mockito.any(UserModel.class))).thenReturn(userAdmin);

        UserModel savedUserAdmin = userService.save(saveUserDtoADMIN, userAdmin);

        Assertions.assertThat(savedUserAdmin).isNotNull();
        Assertions.assertThat(savedUserAdmin.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    @DisplayName("An error should return when trying to create a user with an existing username")
    public void testCreateUserAlreadyExistUsername() {
        String username = "lipezin1";
        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(userUser));

        BusinessException exception = Assertions.catchThrowableOfType(() -> {
            userService.save(saveUserDtoUSER, null);
        }, BusinessException.class);

        Assertions.assertThat(exception.getMessage()).isEqualTo("Username already exists");
    }

    @Test
    @DisplayName("An error should return when trying to create a user with an existing email")
    public void testCreateUserAlreadyExistEmail() {
        String email = "felipe.adriano@jamesapp.com.br";
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(userUser));

        BusinessException exception = Assertions.catchThrowableOfType(() -> {
            userService.save(saveUserDtoUSER, null);
        }, BusinessException.class);

        Assertions.assertThat(exception.getMessage()).isEqualTo("E-mail already exists");
    }

    @Test
    @DisplayName("Deve retornar uma Page de UserModel")
    public void testFindAll() {
        Page<UserModel> users = Mockito.mock(Page.class);

        when(userRepository.findAll(Mockito.any(Pageable.class))).thenReturn(users);

        Pageable pageable = PageRequest.of(1, 10);
        Page<UserModel> saveUser = userService.findAll(pageable);

        Assertions.assertThat(saveUser).isNotNull();
    }


    @Test
    @DisplayName("Should return a user referring to the provided ID")
    public void testFindUserByIdSuccess() {
        UUID id = UUID.fromString("77f4f611-fd7d-4c41-967b-e04fa50d81b5");

        when(userRepository.findById(id)).thenReturn(Optional.of(userUser));

        UserModel savedUser = userService.findById(id);

        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    @DisplayName("Should not return the user when not providing an ID")
    public void testFindUserByIdEmpty() {
        UserModel user = userService.findById(null);

        assertNull(user);
        verifyNoInteractions(userRepository);
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.save(Mockito.any(UserModel.class))).thenReturn(userUser);

        UserModel savedUser = userService.update(userUser, updateUserDto);

        Assertions.assertThat(savedUser).isNotNull();

    }

    @Test
    @DisplayName("Should remove user successfully")
    public void testDeleteUserSuccess() {
        userService.delete(userUser);
        Mockito.verify(userRepository).delete(userUser);
        verifyNoMoreInteractions(userRepository);
    }
}
