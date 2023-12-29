package com.jm.jamesapp.dtos.responses;

import com.jm.jamesapp.models.UserModel;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String username,
        String email) {

    public UserResponseDto(UserModel userModel) {
        this(userModel.getId(), userModel.getName(), userModel.getUsername(), userModel.getEmail());
    }
}
