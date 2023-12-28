package com.jm.jamesapp.services;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.TransactionModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.SaveTransactionDto;
import com.jm.jamesapp.models.dto.UpdateTransactionDto;
import com.jm.jamesapp.repositories.TransactionRepository;
import com.jm.jamesapp.services.exceptions.BusinessException;
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
    public TransactionModel save(SaveTransactionDto saveTransactionDto, UserModel userModel) {
        validateSaveTransaction(saveTransactionDto, userModel);

        TransactionModel transaction = new TransactionModel();
        CustomerModel customer = customerService.findByCpfCnpjAndUser(saveTransactionDto.getCustomerCpfCnpj(), userModel);
        if (customer == null) return null;

        transaction.setCustomer(customer);
        transaction.setUser(userModel);

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
        validateUpdateTransaction(updateTransactionDto, userModel);
        return transactionRepository.save(transaction);
    }

    @Override
    public Page<TransactionModel> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Override
    public Page<TransactionModel> findAllByCustomer(Pageable pageable, CustomerModel customerModel) {
        return transactionRepository.findAllByCustomer(pageable, customerModel);
    }

    @Override
    public List<TransactionModel> findAllByCustomer(CustomerModel customerModel) {
        return transactionRepository.findAllByCustomer(customerModel);
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
        transactionModel.setUpdatedBy(userModel.getId());
        transactionRepository.save(transactionModel);
    }

    private void validateSaveTransaction(SaveTransactionDto saveTransactionDto, UserModel userModel) {
        validateAmountTransaction(saveTransactionDto.getAmount());

        CustomerModel customer = customerService.findByCpfCnpjAndUser(saveTransactionDto.getCustomerCpfCnpj(), userModel);
        if (customer == null) throw new BusinessException("Você não possui um cliente cadastrado com o CPF/CNPJ informado.");

        validateDebitTransaction(saveTransactionDto.getAmount(), customer.getId(), userModel);
    }

    private void validateUpdateTransaction(UpdateTransactionDto updateTransactionDto, UserModel userModel){
        validateAmountTransaction(updateTransactionDto.getAmount());
        CustomerModel customer = customerService.findByCpfCnpjAndUser(updateTransactionDto.getCustomerCpfCnpj(), userModel);
        if (customer == null) throw new BusinessException("Você não possui um cliente cadastrado com o CPF/CNPJ informado.");

        validateDebitTransaction(updateTransactionDto.getAmount(), customer.getId(), userModel);
    }

    private void validateDebitTransaction(BigDecimal transactionAmount, UUID customerId, UserModel userModel){
        if(transactionAmount.compareTo(BigDecimal.ZERO) < 0){
            transactionAmount = transactionAmount.abs();
            BigDecimal balance = customerService.calculateBalance(customerId, userModel);
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
