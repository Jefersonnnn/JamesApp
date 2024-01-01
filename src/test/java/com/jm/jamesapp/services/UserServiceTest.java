package com.jm.jamesapp.services;

import com.jm.jamesapp.models.dto.SaveUserDto;
import com.jm.jamesapp.models.dto.UpdateUserDto;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.user.enums.UserRole;
import com.jm.jamesapp.repositories.UserRepository;
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

    private UserModel user;
    private SaveUserDto saveUserDto;
    private UpdateUserDto updateUserDto;


    @BeforeEach
    public void setUp() {
        user = new UserModel("Felipe Adriano", "lipezin", "felipe.adriano@jamesapp.com.br", "lipe@54321", UserRole.ADMIN);

        saveUserDto = new SaveUserDto();
        saveUserDto.setName("Felipe Adriano");
        saveUserDto.setUsername("lipezin1");
        saveUserDto.setPassword("12345678");
        saveUserDto.setEmail("felipe.adriano@jamesapp.com.br");
        saveUserDto.setRole(UserRole.USER);

        updateUserDto = new UpdateUserDto();
        updateUserDto.setName("Felipe Adriano");
        updateUserDto.setUsername("prestez");
        updateUserDto.setEmail("felipe.adriano@jamesapp.com.br");
    }

    @Test
    @DisplayName("Should create a user successfully")
    public void testCreateUser() {
        when(userRepository.save(Mockito.any(UserModel.class))).thenReturn(user);

        UserModel savedUser = userService.save(saveUserDto, null);

        Assertions.assertThat(savedUser).isNotNull();
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

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

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
        when(userRepository.save(Mockito.any(UserModel.class))).thenReturn(user);

        UserModel savedUser = userService.update(user, updateUserDto);

        Assertions.assertThat(savedUser).isNotNull();

    }

    @Test
    @DisplayName("Should remove user successfully")
    public void testDeleteUserSuccess() {
        userService.delete(user);
        Mockito.verify(userRepository).delete(user);
        verifyNoMoreInteractions(userRepository);
    }

}
