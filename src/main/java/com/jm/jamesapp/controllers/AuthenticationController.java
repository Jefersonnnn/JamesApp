package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.AuthenticationRequestDto;
import com.jm.jamesapp.dtos.requests.UserRequestRecordDto;
import com.jm.jamesapp.dtos.responses.AuthenticationResponseDto;
import com.jm.jamesapp.dtos.responses.UserResponseRecordDto;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.security.TokenService;
import com.jm.jamesapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseRecordDto> register(@RequestBody @Valid UserRequestRecordDto userRequestRecordDto){

        if(this.userService.findByUsername(userRequestRecordDto.username()) != null) throw new DataIntegrityViolationException("User already exists");

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRequestRecordDto.password());
        UserModel newUser = new UserModel(
                userRequestRecordDto.username(),
                userRequestRecordDto.email(),
                encryptedPassword,
                userRequestRecordDto.role()
        );

        UserResponseRecordDto userResponseRecordDto = new UserResponseRecordDto(userService.save(newUser));

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseRecordDto);
    }
}
