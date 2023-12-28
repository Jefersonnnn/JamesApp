package com.jm.jamesapp.repositories;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.TransactionModel;
import com.jm.jamesapp.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, UUID> {
    Page<TransactionModel> findAllByUser(Pageable pageable, UserModel userModel);
    Page<TransactionModel> findAllByCustomer(Pageable pageable, CustomerModel customerModel);
    List<TransactionModel> findAllByCustomer(CustomerModel customerModel);
    Optional<TransactionModel> findByIdAndUser(UUID id, UserModel userModel);
}
