package com.jm.jamesapp.services;

import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.dto.SaveUserDto;
import com.jm.jamesapp.models.dto.UpdateUserDto;
import com.jm.jamesapp.models.user.enums.UserRole;
import com.jm.jamesapp.repositories.UserRepository;
import com.jm.jamesapp.services.exceptions.BusinessException;
import com.jm.jamesapp.services.interfaces.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements IUserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserModel save(SaveUserDto saveUserDto, UserModel userModel) {
        validateSave(saveUserDto, userModel);

        UserModel user = new UserModel();
        user.setName(saveUserDto.getName());
        user.setUsername(formatUsername(saveUserDto.getUsername()));
        user.setEmail(saveUserDto.getEmail());

        String encryptedPassword = new BCryptPasswordEncoder().encode(saveUserDto.getPassword());
        user.setPassword(encryptedPassword);

        if (saveUserDto.getRole() != null && userModel != null && userModel.getRole() == UserRole.ADMIN) {
            user.setRole(saveUserDto.getRole());
        } else {
            user.setRole(UserRole.USER);
        }

        return userRepository.save(user);
    }

    @Override
    public UserModel update(UserModel userModel, UpdateUserDto updateUserDto) {
        if (updateUserDto.getName() != null) userModel.setName(updateUserDto.getName());
        if (updateUserDto.getUsername() != null) userModel.setUsername(formatUsername(updateUserDto.getUsername()));
        if (updateUserDto.getEmail() != null) userModel.setEmail(updateUserDto.getEmail());

        return userRepository.save(userModel);
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Nullable
    public UserModel findById(UUID id) {
        if (id == null) return null;
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(UserModel objModel) {
        userRepository.delete(objModel);
    }

    @Override
    @Nullable
    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    @Nullable
    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    private void validateSave(SaveUserDto saveUserDto, UserModel userModel) {
        if (findByUsername(saveUserDto.getUsername()) != null) throw new BusinessException("Username already exists");
        if (findByEmail(saveUserDto.getEmail()) != null) throw new BusinessException("E-mail already exists");
    }

    private String formatUsername(String username) {
        username = username.replace(" ", "_");
        username = username.toLowerCase();

        return username;
    }
}
