package com.jm.jamesapp.models.dto;

import com.jm.jamesapp.dtos.requests.ApiCustomerRequestDto;
import com.jm.jamesapp.dtos.requests.ApiUserRequestDto;
import com.jm.jamesapp.models.UserModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SaveUserDto {
    private String name;
    private String username;
    private String email;
    private String password;
    private UserModel.UserRole role;

    public SaveUserDto(){}

    public SaveUserDto(ApiUserRequestDto requestDto) {
        this.name = requestDto.name();
        this.username = requestDto.username();
        this.email = requestDto.email();
        this.password = requestDto.password();
        this.role = requestDto.role();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel.UserRole getRole() {
        return role;
    }

    public void setRole(UserModel.UserRole role) {
        this.role = role;
    }
}
