package com.jm.jamesapp.services;

import com.jm.jamesapp.models.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        UserModel user = new UserModel();
        user.setUsername("James Test");
        user.setPassword("123james");
        user.setEmail("jamestest@jamesapp.com.br");

        UserModel createdUser = userService.save(user);

        assert Objects.nonNull(createdUser.getId());
        assert Objects.equals(user.getUsername(), createdUser.getUsername());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void testFindAllUsers(){
        Pageable paginacao = PageRequest.of(0, 10);
        Page<UserModel> userModelList = userService.findAll(paginacao);

        assertNotNull(userModelList);
        assertEquals(userModelList.getTotalElements(), 5);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void testFindUserById() {
        var uuid = UUID.fromString("1c1bfebd-52e3-4d47-8527-bea182851407");
        Optional<UserModel> user = userService.findById(uuid);

        assert user.isPresent();
        assert uuid == user.get().getId();
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void testDeleteUser(){
        var uuid = UUID.fromString("978ea0d1-5207-43de-a778-25a7810d4fa7");
        Optional<UserModel> user = userService.findById(uuid);
        assertNotNull(user);

        userService.delete(user.get());

        Optional<UserModel> deletedUser = userService.findById(uuid);
        assert(deletedUser.isEmpty());
    }
}
