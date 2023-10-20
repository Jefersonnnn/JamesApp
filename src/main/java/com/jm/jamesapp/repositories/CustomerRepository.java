package com.jm.jamesapp.repositories;

import com.jm.jamesapp.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {

    Optional<CustomerModel> findByCpfCnpj(String cpfCnpj);

}
