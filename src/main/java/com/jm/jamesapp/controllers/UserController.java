package com.jm.jamesapp.controllers;


import com.jm.jamesapp.dtos.requests.UserRequestRecordDto;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserRequestRecordDto userRequestRecordDto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRequestRecordDto, userModel);
        // UserVO para enviar para o service
        // Service Retornar o UserModel (BD)
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value="id") UUID id){
        Optional<UserModel> userO = userService.findById(id);
        if(userO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userO.get());

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid UserRequestRecordDto userRequestRecordDto) {
        Optional<UserModel> userO = userService.findById(id);
        if(userO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        var userModel = userO.get();
        BeanUtils.copyProperties(userRequestRecordDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value="id") UUID id){
        Optional<UserModel> userO = userService.findById(id);
        if(userO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        userService.delete(userO.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }

}
