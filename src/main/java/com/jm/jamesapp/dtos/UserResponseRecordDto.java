package com.jm.jamesapp.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserResponseRecordDto(
        @NotNull UUID id,
        @NotBlank String name,
        @NotBlank @Email String email) {
}
