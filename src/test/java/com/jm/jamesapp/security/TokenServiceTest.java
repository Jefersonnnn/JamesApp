package com.jm.jamesapp.security;

import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.user.enums.UserRole;
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
        UserModel userModel = new UserModel("Test User", "test-user-admin", "james.test@james.com.br", "james1234567", UserRole.ADMIN);

        String token = tokenService.generateToken(userModel);
        String subject = tokenService.validateToken(token).toString();

        assertEquals(userModel.getUsername(), subject);
    }
}
