package com.jm.jamesapp.services;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.TransactionModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.UpdateTransactionDto;
import com.jm.jamesapp.repositories.TransactionRepository;
import com.jm.jamesapp.services.interfaces.ITransactionService;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService implements ITransactionService {

    final TransactionRepository transactionRepository;

    final CustomerService customerService;

    public TransactionService(TransactionRepository transactionRepository, CustomerService customerService) {
        this.transactionRepository = transactionRepository;
        this.customerService = customerService;
    }

    @Override
    public Page<TransactionModel> findAllByUser(Pageable pageable, UserModel userModel) {
        return transactionRepository.findAllByUser(pageable, userModel);
    }

    @Override
    public TransactionModel save(TransactionModel transaction) {
        //Todo: Colocar no padrão de entrada de dados
        try {
            var benifitedCustomer = customerService.findById(transaction.getCustomer().getId());
            // Todo: se o cara receber o dinheiro de alguem que não está cadastrado?
            if (benifitedCustomer == null){
                return null;
            }

            if (transaction.getTypeTransaction() == TransactionModel.TypeTransaction.PAYMENT_RECEIVED){
                customer.setBalance(customer.getBalance().add(transaction.getAmount()));
            } else if (transaction.getTypeTransaction() == TransactionModel.TypeTransaction.PAID_GROUPBILL) {
                customer.setBalance(customer.getBalance().subtract(transaction.getAmount()));
            }
            customerService.update(customer, ownerUser);
        } catch (TransactionalException ex) {
            transaction.setStatus(TransactionModel.StatusTransaction.ERROR);
        }

        transaction.setStatus(TransactionModel.StatusTransaction.COMPLETED);
        return transactionRepository.save(transaction);
    }

    @Override
    public TransactionModel update(TransactionModel transaction, UpdateTransactionDto updateTransactionDto, UserModel userModel) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Page<TransactionModel> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Override
    public TransactionModel findByIdAndUser(UUID id, UserModel userModel) {
        return transactionRepository.findByIdAndUser(id, userModel).orElse(null);
    }

    @Override
    @Transactional
    public void delete(TransactionModel transaction) {
        transactionRepository.delete(transaction);
    }

    @Override
    public double getBalanceFromCustomer(CustomerModel customerModel){
        //TODO FAZER
        return 0.0;
    }

    @Override
    public double getBalanceFromUser(UserModel userModel) {
        //TODO: Fazer
        return 0;
    }
}
