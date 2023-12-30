package com.jm.jamesapp.services;

import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.repositories.UserRepository;
import com.jm.jamesapp.utils.constraints.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    UserRepository userRepository;

    UserModel user;

    @BeforeEach
    public void setUp() {
        user = new UserModel("Felipe Adriano", "felipe.adriano@jamesapp.com.br", "lipe@54321", UserModel.UserRole.ADMIN);
        user.setId(UUID.fromString("1c1bfebd-52e3-4d47-8527-bea182851407"));
    }

    @Test
    @DisplayName("Should create a user successfully")
    public void testCreateUser() {
        when(userRepository.save(user)).thenReturn(user);

        UserModel createdUser = userService.save(user, userModel);

        assertNotNull(createdUser.getId());
        assertEquals(user.getUsername(), createdUser.getUsername());
    }

    @Test
    @DisplayName("Should retrieve all users when paginating")
    public void testFindAllUsers(){
        Pageable paginacao = PageRequest.of(0, 10);
        List<UserModel> userModels = new ArrayList<>();
        userModels.add(new UserModel("Felipe Adriano", "felipe.adriano@jamesapp.com.br", "lipe@54321", UserModel.UserRole.USER));
        userModels.add(new UserModel("Zezin Rei Delas", "zezin.reidelas@jamesapp.com.br", "zezin@54321", UserModel.UserRole.USER));
        userModels.add(new UserModel("James App", "james.app@jamesapp.com.br", "james@5432", UserModel.UserRole.ADMIN));

        Page<UserModel> page = new PageImpl<>(userModels, paginacao, userModels.size());

        when(userRepository.findAll(paginacao)).thenReturn(page);

        Page<UserModel> userModelList = userService.findAll(paginacao);

        assertNotNull(userModelList);
        assertEquals(userModelList.getTotalElements(), 3);
    }

    @Test
    @DisplayName("Should return a user referring to the provided ID")
    public void testFindUserByIdSuccess() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        var uuid = UUID.fromString("1c1bfebd-52e3-4d47-8527-bea182851407");
        UserModel user = userService.findById(uuid);

        assert user != null;
        assertEquals(uuid,user.getId());
        verify(userRepository).findById(uuid);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Should not return the user when not providing an ID")
    public void testFindUserByIdEmpty() {
        when(userRepository.findById(null)).thenReturn(Optional.empty());

       UserModel user = userService.findById(null);

        assertNotNull(user);
        verifyNoInteractions(userRepository);
    }

    @Test
    @DisplayName("Should remove user successfully")
    public void testDeleteUserSuccess(){
        userService.delete(user);
        Mockito.verify(userRepository).delete(user);
        verifyNoMoreInteractions(userRepository);
    }

}
