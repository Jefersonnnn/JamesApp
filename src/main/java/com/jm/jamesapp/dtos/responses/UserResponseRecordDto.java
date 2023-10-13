package com.jm.jamesapp.dtos.responses;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserResponseRecordDto(
        UUID id,
        String name,
        String email) {
}
