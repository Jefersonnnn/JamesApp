package com.jm.jamesapp.repositories;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {

    Optional<CustomerModel> findByIdAndUser(UUID id, UserModel userModel);
    Optional<CustomerModel> findByCpfCnpj(String cpfCnpj);
    Optional<CustomerModel> findByCpfCnpjAndUser(String cpfCnpj, UserModel userModel);
    List<CustomerModel> findAllByUser(UserModel userModel);
}
