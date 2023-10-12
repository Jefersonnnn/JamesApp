package com.jm.jamesapp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface IBaseService<T> {
    T save(T objModel);

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(UUID id);

    void delete(T objModel);
}
