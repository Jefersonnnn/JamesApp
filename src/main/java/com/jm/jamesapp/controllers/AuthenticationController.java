package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.AuthenticationRequestDto;
import com.jm.jamesapp.dtos.requests.UserRequestRecordDto;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private final UserRepository userRepository;

    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequestDto authenticationDto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.username(), authenticationDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRequestRecordDto userRequestRecordDto){
        if(this.userRepository.findByUsername(userRequestRecordDto.username()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRequestRecordDto.password());
        UserModel newUser = new UserModel(userRequestRecordDto.username(), userRequestRecordDto.email(), encryptedPassword, userRequestRecordDto.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
