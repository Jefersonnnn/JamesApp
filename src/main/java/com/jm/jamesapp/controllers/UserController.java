package com.jm.jamesapp.controllers;


import com.jm.jamesapp.dtos.requests.ApiUpdateUserRequestDto;
import com.jm.jamesapp.dtos.responses.UserResponseDto;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.dto.UpdateUserDto;
import com.jm.jamesapp.models.user.enums.UserRole;
import com.jm.jamesapp.security.IAuthenticationFacade;
import com.jm.jamesapp.security.exceptions.UnauthorizedException;
import com.jm.jamesapp.services.exceptions.ObjectNotFoundException;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    private final IAuthenticationFacade authenticationFacade;

    final IUserService userService;

    public UserController(IUserService userService, IAuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

//    @PostMapping
//    public ResponseEntity<UserResponseDto> save(@RequestBody @Valid ApiUserRequestDto apiUserRequestDto) {
//        if(this.userService.findByUsername(apiUserRequestDto.username()) != null) throw new DataIntegrityViolationException("Username already exists");
//        if(this.userService.findByEmail(apiUserRequestDto.email()) != null) throw new DataIntegrityViolationException("E-mail already exists");
//
//        var userSaved = userService.save(new SaveUserDto(apiUserRequestDto));
//        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(userSaved));
//    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        Page<UserModel> userList = userService.findAll(pageable);
        Page<UserResponseDto> pageResponse = userList.map(UserResponseDto::new);

        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getOne(@PathVariable(value="id") UUID id){
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();
        UserModel userO = userService.findById(id);
        if(userO == null) throw new ObjectNotFoundException(id, "user");
        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDto(userO));
    }

    @GetMapping("/byEmail")
    public ResponseEntity<UserResponseDto> getByEmail(@RequestParam(value = "email", defaultValue = "") String email){
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        if(email.isEmpty()) throw new ObjectNotFoundException(email, "user");

        UserModel userO = (UserModel) userService.findByEmail(email);
        if(userO == null) throw new ObjectNotFoundException(email, "user");

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDto(userO));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMe(){
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();
        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDto(userModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable(value="id") UUID id, @RequestBody @Valid ApiUpdateUserRequestDto apiRequestDto) {
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        UserModel userToUpdate = null;
        if (userModel.getRole() == UserRole.ADMIN){
            userToUpdate = userService.findById(id);
        } else if (id == userModel.getId()){
            userToUpdate = userService.findById(id);
        }

        if(userToUpdate == null) throw new ObjectNotFoundException(id, "user");

        userToUpdate = userService.update(userToUpdate, new UpdateUserDto(apiRequestDto));

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDto(userToUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value="id") UUID id){
        UserModel userModel = (UserModel) authenticationFacade.getAuthentication().getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        //Todo: Tirar IF criar nova rota admin
        UserModel userToDelete = null;
        if(userModel.getRole() == UserRole.ADMIN){
            userToDelete = userService.findById(id);
        } else if (id == userModel.getId()){
            userToDelete = userService.findById(id);
        }

        if(userToDelete == null) throw new ObjectNotFoundException(id, "user");

        userService.delete(userToDelete);
        return ResponseEntity.noContent().build();


    }

}
