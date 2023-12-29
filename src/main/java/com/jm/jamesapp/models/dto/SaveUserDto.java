package com.jm.jamesapp.models.dto;

import com.jm.jamesapp.dtos.requests.ApiUserRequestDto;
import com.jm.jamesapp.models.user.enums.UserRole;

public class SaveUserDto {
    private String name;
    private String username;
    private String email;
    private String password;
    private UserRole role;

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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
