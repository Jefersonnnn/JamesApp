package com.jm.jamesapp.services;

import com.jm.jamesapp.models.TransactionModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.repositories.TransactionRepository;
import com.jm.jamesapp.services.interfaces.ITransactionService;
import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<TransactionModel> findAllByOwner(UserModel userModel) {
        return transactionRepository.findAllByOwner(userModel);
    }

    @Override
    @Transactional
    public TransactionModel save(TransactionModel transaction) {
        try {
            var benifitedCustomer = customerService.findById(transaction.getCustomer().getId());
            if (benifitedCustomer.isEmpty()){
                return null;
            }

            var customer = benifitedCustomer.get();
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
    public TransactionModel update(TransactionModel transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Page<TransactionModel> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Override
    public UserModel findById(UUID id) {
        return transactionRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(TransactionModel transaction) {
        transactionRepository.delete(transaction);
    }


}
