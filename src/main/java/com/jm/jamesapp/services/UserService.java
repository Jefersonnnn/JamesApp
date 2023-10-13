package com.jm.jamesapp.services;

import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.repositories.UserRepository;
import com.jm.jamesapp.services.interfaces.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public UserModel save(UserModel objModel) {
        return userRepository.save(objModel);
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<UserModel> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(UserModel objModel) {
        userRepository.delete(objModel);
    }
}
