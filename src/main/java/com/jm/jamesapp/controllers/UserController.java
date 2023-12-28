package com.jm.jamesapp.controllers;


import com.jm.jamesapp.dtos.requests.ApiUpdateUserRequestDto;
import com.jm.jamesapp.dtos.requests.ApiUserRequestDto;
import com.jm.jamesapp.dtos.responses.UserResponseDto;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.SaveUserDto;
import com.jm.jamesapp.models.dto.UpdateUserDto;
import com.jm.jamesapp.security.exceptions.UnauthorizedException;
import com.jm.jamesapp.services.exceptions.ObjectNotFoundException;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody @Valid ApiUserRequestDto apiUserRequestDto) {
        if(this.userService.findByUsername(apiUserRequestDto.username()) != null) throw new DataIntegrityViolationException("Username already exists");
        if(this.userService.findByEmail(apiUserRequestDto.email()) != null) throw new DataIntegrityViolationException("E-mail already exists");

        var userSaved = userService.save(new SaveUserDto(apiUserRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(userSaved));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        var userList = userService.findAll(pageable);

        if(userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var responseList = new ArrayList<UserResponseDto>();

        for (var user: userList) {
            responseList.add(new UserResponseDto(user));
        }

        Page<UserResponseDto> pageResponse = new PageImpl<>(responseList, pageable, responseList.size());
        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getOneUser(@PathVariable(value="id") UUID id, Authentication authentication){
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();
        UserModel userO = userService.findById(id);
        if(userO == null) throw new ObjectNotFoundException(id, "user");
        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDto(userO));
    }

    @GetMapping("/byEmail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getByEmail(@RequestParam(value = "email", defaultValue = "") String email, Authentication authentication){
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        if(email.isEmpty()) throw new ObjectNotFoundException(email, "user");

        UserModel userO = (UserModel) userService.findByEmail(email);
        if(userO == null) throw new ObjectNotFoundException(email, "user");

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDto(userO));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMe(Authentication authentication){
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();
        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDto(userModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable(value="id") UUID id, @RequestBody @Valid ApiUpdateUserRequestDto apiRequestDto, Authentication authentication) {
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        UserModel userToUpdate;
        if (userModel.getRole() == UserModel.UserRole.ADMIN){
            userToUpdate = userService.findById(id);
        } else {
            userToUpdate = userService.findById(userModel.getId());
        }

        if(userToUpdate == null) throw new ObjectNotFoundException(id, "user");

        userToUpdate = userService.update(userToUpdate, new UpdateUserDto(apiRequestDto));

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDto(userToUpdate));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    //Todo Colocar regras para deletar o proprio usuário (não sendo admin)
    public ResponseEntity<Object> deleteUser(@PathVariable(value="id") UUID id, Authentication authentication){
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        UserModel userO = userService.findById(id);
        if(userO == null) throw new ObjectNotFoundException(id, "user");

        userService.delete(userO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
