package com.jm.jamesapp.repositories;

import com.jm.jamesapp.dtos.requests.ApiUserRequestDto;
import com.jm.jamesapp.models.user.UserModel;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get User successfully from DB")
    void findByIdSuccess(){
        ApiUserRequestDto data = new ApiUserRequestDto(
                "James",
                "james.test@james.com.br",
                "super-secret-password",
                UserModel.UserRole.USER);
        UserModel newUser = this.createUser(data);

        var uuid = newUser.getId();

        Optional<UserModel> result = this.userRepository.findById(uuid);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(uuid);

    }

    @Test
    @DisplayName("Should not get User from DB when user not exists")
    void findByIdNotFound(){
        var uuid = UUID.randomUUID();

        Optional<UserModel> result = this.userRepository.findById(uuid);

        assertThat(result.isEmpty()).isTrue();
    }

    private UserModel createUser(ApiUserRequestDto data){
        UserModel newUser = new UserModel(data);
        this.entityManager.persist(newUser);
        return newUser;
    }
}