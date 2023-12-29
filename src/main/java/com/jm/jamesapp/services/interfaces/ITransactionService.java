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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ITransactionService {

    Page<TransactionModel> findAllByUser(Pageable pageable, UserModel userModel);

    @Transactional
    TransactionModel register(SaveTransactionDto saveTransactionDto);

    @Transactional
    TransactionModel update(TransactionModel transaction, UpdateTransactionDto updateTransactionDto, UserModel userModel);

    Page<TransactionModel> findAll(Pageable pageable);

    Page<TransactionModel> findAllByCustomerAndUser(Pageable pageable, CustomerModel customer, UserModel user);

    List<TransactionModel> findAllByCustomerAndUser(CustomerModel customer, UserModel user);

    @Transactional
    void delete(TransactionModel transactionModel);

    @Transactional
    void cancel(TransactionModel transactionModel, UserModel userModel);

    @Nullable
    TransactionModel findByIdAndUser(UUID id, UserModel ownerUser);

}
