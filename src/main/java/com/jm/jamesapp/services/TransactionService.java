package com.jm.jamesapp.services;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.TransactionModel;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.models.dto.SaveTransactionDto;
import com.jm.jamesapp.models.dto.UpdateTransactionDto;
import com.jm.jamesapp.repositories.TransactionRepository;
import com.jm.jamesapp.services.exceptions.BusinessException;
import com.jm.jamesapp.services.exceptions.ObjectNotFoundException;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.ITransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService implements ITransactionService {

    final TransactionRepository transactionRepository;

    final ICustomerService customerService;

    public TransactionService(TransactionRepository transactionRepository, ICustomerService customerService) {
        this.transactionRepository = transactionRepository;
        this.customerService = customerService;
    }

    @Override
    public Page<TransactionModel> findAllByUser(Pageable pageable, UserModel userModel) {
        return transactionRepository.findAllByUser(pageable, userModel);
    }

    @Override
    public TransactionModel register(SaveTransactionDto saveTransactionDto) {
        validateSaveTransaction(saveTransactionDto);

        TransactionModel transaction = new TransactionModel();
        CustomerModel customer = saveTransactionDto.getSender();
        if (customer == null) return null;

        transaction.setCustomer(customer);
        transaction.setUser(saveTransactionDto.getSender().getUser());

        transaction.setDueDate(saveTransactionDto.getDueDate());
        transaction.setAmount(saveTransactionDto.getAmount());
        transaction.setAutomatic(false);
        transaction.setDescription(saveTransactionDto.getDescription());

        if (saveTransactionDto.getAmount().compareTo(BigDecimal.ZERO) < 0){
            transaction.setTypeTransaction(TransactionModel.TypeTransaction.PAID_GROUPBILL);
        } else {
            transaction.setTypeTransaction(TransactionModel.TypeTransaction.PAYMENT_RECEIVED);
        }

        transaction.setStatus(TransactionModel.StatusTransaction.COMPLETED);


        return transactionRepository.save(transaction);
    }

    @Override
    public TransactionModel update(TransactionModel transaction, UpdateTransactionDto updateTransactionDto, UserModel userModel) {
        // todo: Faz sentido alterar uma transação?
        // Não poderia quebrar todas as posteriores caso pegar uma antiga.
        validateUpdateTransaction(updateTransactionDto);
        return transactionRepository.save(transaction);
    }

    @Override
    public Page<TransactionModel> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Override
    public Page<TransactionModel> findAllByCustomerAndUser(Pageable pageable, CustomerModel customer, UserModel user) {
        return transactionRepository.findAllByCustomerAndUser(pageable, customer, user);
    }

    @Override
    public List<TransactionModel> findAllByCustomerAndUser(CustomerModel customer, UserModel user) {
        return transactionRepository.findAllByCustomerAndUser(customer, user);
    }

    @Override
    public TransactionModel findByIdAndUser(UUID id, UserModel userModel) {
        return transactionRepository.findByIdAndUser(id, userModel).orElse(null);
    }

    @Override
    public void delete(TransactionModel transaction) {
        transactionRepository.delete(transaction);
    }

    @Override
    public void cancel(TransactionModel transactionModel, UserModel userModel) {
        transactionModel.setStatus(TransactionModel.StatusTransaction.CANCELED);
        transactionRepository.save(transactionModel);
    }

    private void validateSaveTransaction(SaveTransactionDto saveTransactionDto){
        var customerSender = saveTransactionDto.getSender();
        var user = saveTransactionDto.getSender().getUser();

        boolean isCustomerAlreadyTransaction = customerService.findByIdAndUser(customerSender.getId(), user) != null;
        if (!isCustomerAlreadyTransaction) throw new ObjectNotFoundException(customerSender.getId(), "customer");

        validateAmountTransaction(saveTransactionDto.getAmount());
        validateDebitTransaction(saveTransactionDto.getAmount(), saveTransactionDto.getSender());
    }

    private void validateUpdateTransaction(UpdateTransactionDto updateTransactionDto){
        var customerSender = updateTransactionDto.getSender();
        var user = updateTransactionDto.getSender().getUser();

        boolean isCustomerAlreadyTransaction = customerService.findByIdAndUser(customerSender.getId(), user) != null;
        if (!isCustomerAlreadyTransaction) throw new ObjectNotFoundException(customerSender.getId(), "customer");

        validateAmountTransaction(updateTransactionDto.getAmount());
        validateDebitTransaction(updateTransactionDto.getAmount(), updateTransactionDto.getSender());
    }

    private void validateDebitTransaction(BigDecimal transactionAmount, CustomerModel customer){
        if(transactionAmount.compareTo(BigDecimal.ZERO) < 0){
            transactionAmount = transactionAmount.abs();
            BigDecimal balance = customerService.calculateBalance(customer);
            if(balance.compareTo(transactionAmount) < 0){
                throw new BusinessException("Saldo insuficiente para realizar a transação de débito.");
            }
        }
    }

    private void validateAmountTransaction(BigDecimal transactionAmount){
        if(transactionAmount.compareTo(BigDecimal.ZERO) == 0){
            throw new BusinessException("O valor da transação deve ser diferente de zero.");
        }
    }
}
