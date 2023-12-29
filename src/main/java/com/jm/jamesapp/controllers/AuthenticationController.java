package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.ApiUserRequestDto;
import com.jm.jamesapp.dtos.requests.AuthenticationRequestDto;
import com.jm.jamesapp.dtos.responses.AuthenticationResponseDto;
import com.jm.jamesapp.dtos.responses.UserResponseDto;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.SaveUserDto;
import com.jm.jamesapp.security.TokenService;
import com.jm.jamesapp.services.UserService;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final IUserService userService;

    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody @Valid AuthenticationRequestDto authenticationDto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.username(), authenticationDto.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new AuthenticationResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid ApiUserRequestDto apiUserRequestDto){

        if(this.userService.findByUsername(apiUserRequestDto.username()) != null) throw new DataIntegrityViolationException("Username already exists");
        if(this.userService.findByEmail(apiUserRequestDto.email()) != null) throw new DataIntegrityViolationException("E-mail already exists");

        var newUser = userService.save(new SaveUserDto(apiUserRequestDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(newUser));
    }
}
