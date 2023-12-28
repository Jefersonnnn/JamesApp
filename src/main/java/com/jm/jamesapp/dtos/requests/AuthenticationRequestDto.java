package com.jm.jamesapp.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthenticationRequestDto(
        @NotBlank(message = "Invalid username: Empty username") String username,
        @NotNull(message = "Invalid password: Empty password") @Size(min = 8, message = "Invalid Name: Must have at least 8 characters") String password

) {
}
