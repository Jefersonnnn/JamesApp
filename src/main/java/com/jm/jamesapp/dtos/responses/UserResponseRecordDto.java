package com.jm.jamesapp.dtos.responses;

import com.jm.jamesapp.models.UserModel;

import java.util.UUID;

public record UserResponseRecordDto(
        UUID id,
        String name,
        String email) {

    public UserResponseRecordDto (UserModel userModel) {
        this(userModel.getId(), userModel.getName(), userModel.getEmail());
    }
}
