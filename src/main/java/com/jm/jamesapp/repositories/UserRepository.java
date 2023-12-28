package com.jm.jamesapp.repositories;

import com.jm.jamesapp.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserDetails> findByUsername(String username);
    Optional<UserDetails> findByEmail(String email);
    Boolean existsByEmail(String email);
}
