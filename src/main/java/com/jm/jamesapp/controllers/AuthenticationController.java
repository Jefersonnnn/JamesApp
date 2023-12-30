package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.ApiUserRequestDto;
import com.jm.jamesapp.dtos.requests.AuthenticationRequestDto;
import com.jm.jamesapp.dtos.responses.AuthenticationResponseDto;
import com.jm.jamesapp.dtos.responses.UserResponseDto;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.dto.SaveUserDto;
import com.jm.jamesapp.security.IAuthenticationFacade;
import com.jm.jamesapp.security.TokenService;
import com.jm.jamesapp.services.UserService;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final IAuthenticationFacade authenticationFacade;
    private final IUserService userService;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, IAuthenticationFacade authenticationFacade, UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody @Valid AuthenticationRequestDto authenticationDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.username(), authenticationDto.password());

        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new AuthenticationResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid ApiUserRequestDto apiUserRequestDto) {
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();

        UserModel newUser = userService.save(new SaveUserDto(apiUserRequestDto), userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(newUser));
    }
}
