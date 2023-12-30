package com.jm.jamesapp.services.interfaces;


import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.transaction.TransactionModel;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.dto.SaveTransactionDto;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ITransactionService {

    Page<TransactionModel> findAllByUser(Pageable pageable, UserModel userModel);

    @Transactional
    TransactionModel register(CustomerModel customer, SaveTransactionDto saveTransactionDto);

    Page<TransactionModel> findAll(Pageable pageable);

    Page<TransactionModel> findAllByCustomerAndUser(Pageable pageable, CustomerModel customer, UserModel user);

    @Nullable
    TransactionModel findByIdAndUser(UUID id, UserModel ownerUser);

}
