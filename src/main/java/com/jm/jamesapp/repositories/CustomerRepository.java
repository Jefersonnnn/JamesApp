package com.jm.jamesapp.repositories;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.user.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {

    Optional<CustomerModel> findByIdAndUser(UUID id, UserModel userModel);
    Optional<CustomerModel> findByCpfCnpjAndUser(String cpfCnpj, UserModel userModel);
    Page<CustomerModel> findAllByUser(Pageable pageable, UserModel userModel);
    @Query("SELECT SUM(t.amount) FROM TransactionModel t WHERE t.customer = :customer")
    BigDecimal sumTransactionsByCustomer(CustomerModel customer);

}
