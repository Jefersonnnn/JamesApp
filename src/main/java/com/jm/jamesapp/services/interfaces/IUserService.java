package com.jm.jamesapp.services.interfaces;

import com.jm.jamesapp.models.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface IUserService{
    @Transactional
    UserModel save(UserModel objModel);

    @Transactional
    UserModel update(UserModel objModel);

    Page<UserModel> findAll(Pageable pageable);

    @Transactional
    void delete(UserModel objModel);

    @Nullable
    UserDetails findByUsername(String username);

    UserModel findById(UUID id);
}
