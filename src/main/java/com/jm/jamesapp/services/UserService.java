package com.jm.jamesapp.services;

import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.SaveUserDto;
import com.jm.jamesapp.models.dto.UpdateUserDto;
import com.jm.jamesapp.repositories.UserRepository;
import com.jm.jamesapp.services.interfaces.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserModel save(SaveUserDto saveUserDto) {
        UserModel user = new UserModel();

        user.setName(saveUserDto.getName());
        user.setUsername(saveUserDto.getUsername());
        user.setEmail(saveUserDto.getEmail());

        String encryptedPassword = new BCryptPasswordEncoder().encode(saveUserDto.getPassword());
        user.setPassword(encryptedPassword);

        if (saveUserDto.getRole() == null){
            user.setRole(UserModel.UserRole.USER);
        } else {
            user.setRole(saveUserDto.getRole());
        }

        return userRepository.save(user);
    }

    @Override
    public UserModel update(UserModel userModel, UpdateUserDto updateUserDto) {
        if (updateUserDto.getName() != null) userModel.setName(updateUserDto.getName());
        if (updateUserDto.getUsername() != null) userModel.setUsername(updateUserDto.getUsername());
        if (updateUserDto.getEmail() != null) userModel.setEmail(updateUserDto.getEmail());

        return userRepository.save(userModel);
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public UserModel findById(UUID id) {
        if (id == null) return null;
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(UserModel objModel) {
        userRepository.delete(objModel);
    }

    @Override
    public UserDetails findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
