package com.jm.jamesapp.dtos.requests;

import jakarta.validation.constraints.Email;

public record ApiUpdateUserRequestDto(
        String name,
        String username,
        @Email(message = "Invalid email") String email
) {
}
