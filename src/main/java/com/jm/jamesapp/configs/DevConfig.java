package com.jm.jamesapp.configs;

import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.repositories.UserRepository;
import com.jm.jamesapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();

        UserModel userAdm = new UserModel("Jeferson Machado", "jeferson.machado@jamesapp.com.br", "12345678", UserModel.UserRole.ADMIN);
        UserModel user1 = new UserModel("Douglas Giovanellas", "doug.giova.nellas@jamesapp.com.br", "12345678", UserModel.UserRole.USER);
        UserModel user2 = new UserModel("Felipe Prestes", "felipe.prestes@jamesapp.com.br", "12345678", UserModel.UserRole.USER);
        UserModel user3 = new UserModel("José Raul", "jose.raqul.quadross@jamesapp.com.br", "12345678", UserModel.UserRole.USER);

        userRepository.saveAll(Arrays.asList(userAdm, user1, user2, user3));
    }
}
