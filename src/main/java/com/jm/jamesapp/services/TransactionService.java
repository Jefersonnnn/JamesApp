package com.jm.jamesapp.services;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.dto.SaveTransactionDto;
import com.jm.jamesapp.models.transaction.TransactionModel;
import com.jm.jamesapp.models.transaction.enums.TransactionOrigin;
import com.jm.jamesapp.models.transaction.enums.TransactionType;
import com.jm.jamesapp.models.user.UserModel;
import com.jm.jamesapp.repositories.TransactionRepository;
import com.jm.jamesapp.services.exceptions.BusinessException;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.ITransactionService;
import com.jm.jamesapp.utils.BigDecimalUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public TransactionModel register(CustomerModel customer, SaveTransactionDto saveTransactionDto) {
        validateSaveTransaction(customer, saveTransactionDto);

        TransactionModel transaction = new TransactionModel();
        transaction.setCustomer(customer);
        transaction.setUser(customer.getUser());
        transaction.setPaymentDate(saveTransactionDto.getPaymentDate());
        transaction.setAmount(saveTransactionDto.getAmount());
        transaction.setDescription(saveTransactionDto.getDescription());
        transaction.setOrigin(TransactionOrigin.MANUAL);
        transaction.setType(saveTransactionDto.getType());

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
    public TransactionModel findByIdAndUser(UUID id, UserModel userModel) {
        return transactionRepository.findByIdAndUser(id, userModel).orElse(null);
    }

    private void validateSaveTransaction(CustomerModel customer, SaveTransactionDto saveTransactionDto){
        validateAmountTransaction(saveTransactionDto.getAmount(), saveTransactionDto.getType());

        if (BigDecimalUtils.isNegative(saveTransactionDto.getAmount())) {
            validateDebitTransaction(saveTransactionDto.getAmount(), customer);
        }
    }

    private void validateDebitTransaction(BigDecimal transactionAmount, CustomerModel customer){
        // TODO: Testar se o abs não mexe na instância recebida
        transactionAmount = transactionAmount.abs();
        BigDecimal balance = customerService.calculateBalance(customer);

        if (transactionAmount.compareTo(balance) == 1) {
            throw new BusinessException("Saldo insuficiente para realizar a transação de débito.");
        }
    }

    private void validateAmountTransaction(BigDecimal transactionAmount, TransactionType type){
        if (BigDecimalUtils.isZero(transactionAmount)){
            throw new BusinessException("O valor da transação deve ser diferente de zero.");
        }

        if (BigDecimalUtils.isNegative(transactionAmount)) {
            if (!type.getIsNegative()) throw new BusinessException("Valor da transação informado é inválido para o tipo informado.");
        } else if (type.getIsNegative()) {
            throw new BusinessException("Valor da transação informado é inválido para o tipo informado.");
        }
    }
}
