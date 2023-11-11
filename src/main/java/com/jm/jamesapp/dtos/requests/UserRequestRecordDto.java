package com.jm.jamesapp.dtos.requests;

import com.jm.jamesapp.utils.constraints.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestRecordDto(@NotBlank(message = "Invalid username: Empty username") String username,
                                   @NotBlank(message = "Invalid email: Empty email") @Email(message = "Invalid email") String email,
                                   @NotNull(message = "Invalid password: Empty password") @Size(min = 8, message = "Invalid Name: Must have at least 8 characters") String password,
                                   UserRole role
) {
}
