package com.jm.jamesapp.dtos.responses;

import java.util.UUID;

public record UserResponseRecordDto(
        UUID id,
        String name,
        String email) {

    public UserResponseRecordDto {
    }
}
