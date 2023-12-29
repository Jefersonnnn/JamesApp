package com.jm.jamesapp.services.interfaces;

import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.SaveUserDto;
import com.jm.jamesapp.models.dto.UpdateUserDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface IUserService{
    @Transactional
    UserModel save(SaveUserDto saveUserDto);

    @Transactional
    UserModel update(UserModel objModel, UpdateUserDto updateUserDto);

    Page<UserModel> findAll(Pageable pageable);

    @Transactional
    void delete(UserModel objModel);

    @Nullable
    UserDetails findByUsername(String username);

    @Nullable
    UserDetails findByEmail(String email);

    @Nullable
    UserModel findById(UUID id);
}
