package com.jm.jamesapp.models.dto;

import com.jm.jamesapp.dtos.requests.ApiUpdateUserRequestDto;

public class UpdateUserDto {
    private String name;
    private String username;
    private String email;

    public UpdateUserDto(){}

    public UpdateUserDto(ApiUpdateUserRequestDto requestDto) {
        this.name = requestDto.name();
        this.username = requestDto.username();
        this.email = requestDto.email();
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

}
