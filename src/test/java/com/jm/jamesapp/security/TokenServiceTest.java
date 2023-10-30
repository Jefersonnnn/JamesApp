package com.jm.jamesapp.security;

import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.utils.constraints.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Test
    public void testGenerateAndValidateToken(){
        UserModel userModel = new UserModel("test-user-admin", "james.test@james.com.br", "james1234567", UserRole.ADMIN);

        String token = tokenService.generateToken(userModel);
        String subject = tokenService.validateToken(token);

        assertEquals(userModel.getUsername(), subject);
    }
}
