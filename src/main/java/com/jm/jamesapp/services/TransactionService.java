package com.jm.jamesapp.services;

import com.jm.jamesapp.models.TransactionModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.repositories.TransactionRepository;
import com.jm.jamesapp.services.interfaces.ITransactionService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService implements ITransactionService {

    final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public TransactionModel register(TransactionModel transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionModel> findAllByOwner(UserModel userModel) {
        return transactionRepository.findAllByOwner(userModel);
    }

    @Override
    public TransactionModel save(TransactionModel transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public TransactionModel update(TransactionModel transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Page<TransactionModel> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Override
    public Optional<TransactionModel> findById(UUID id) {
        return transactionRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(TransactionModel transaction) {
        transactionRepository.delete(transaction);
    }


}
