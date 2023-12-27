package com.jm.jamesapp.services.interfaces;


import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.TransactionModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.SaveTransactionDto;
import com.jm.jamesapp.models.dto.UpdateTransactionDto;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ITransactionService {

    Page<TransactionModel> findAllByUser(Pageable pageable, UserModel userModel);

    double getBalanceFromCustomer(CustomerModel customerModel);

    double getBalanceFromUser(UserModel userModel);

    @Transactional
    TransactionModel save(SaveTransactionDto saveTransactionDto, UserModel userModel);

    @Transactional
    TransactionModel update(TransactionModel transaction, UpdateTransactionDto updateTransactionDto, UserModel userModel);

    Page<TransactionModel> findAll(Pageable pageable);

    @Transactional
    void delete(TransactionModel transactionModel);

    @Transactional
    void cancel(TransactionModel transactionModel, UserModel userModel);

    @Nullable
    TransactionModel findByIdAndUser(UUID id, UserModel ownerUser);
}
