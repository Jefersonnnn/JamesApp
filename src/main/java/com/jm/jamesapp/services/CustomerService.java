package com.jm.jamesapp.services;

import com.jm.jamesapp.models.dto.SaveCustomerDto;
import com.jm.jamesapp.models.dto.UpdateCustomerDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.repositories.CustomerRepository;
import com.jm.jamesapp.services.exceptions.BusinessException;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.utils.constraints.CpfOrCnpjValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.jm.jamesapp.utils.constraints.CpfOrCnpjValidator.cleanStringValue;

@Service
public class CustomerService implements ICustomerService {

    final CustomerRepository customerRepository;

    final TransactionService transactionService;

    public CustomerService(CustomerRepository customerRepository, TransactionService transactionService) {
        this.customerRepository = customerRepository;
        this.transactionService = transactionService;
    }

    @Override
    public CustomerModel save(SaveCustomerDto saveCustomerDto, UserModel userModel) {
        saveCustomerDto.setCpfCnpj(cleanStringValue(saveCustomerDto.getCpfCnpj()));

        validateSave(saveCustomerDto, userModel);

        CustomerModel customer = new CustomerModel(userModel, saveCustomerDto.getName(), saveCustomerDto.getCpfCnpj());

        return customerRepository.save(customer);
    }

    @Override
    public CustomerModel update(CustomerModel customer, UpdateCustomerDto saveCustomerDto, UserModel userModel) {
        validateUpdate(saveCustomerDto, userModel);

        customer.setName(saveCustomerDto.getName());
        customer.setCpfCnpj(cleanStringValue(saveCustomerDto.getCpfCnpj()));
        customer.setUpdatedBy(userModel.getId());

        return customerRepository.save(customer);
    }

    @Override
    public CustomerModel findById(UUID id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(CustomerModel customerModel) {
        double balanceFromCustomer = transactionService.getBalanceFromCustomer(customerModel);
        if (balanceFromCustomer > 0) throw new BusinessException("Cliente possui saldo pendente");
        // TODO: mas se o guerreiro quiser excluir mesmo assim?
        // TODO: transactionService.calculateCustomerBalance(customerModel) ... valida se tem saldo

        customerRepository.delete(customerModel);
    }

    @Override
    public CustomerModel findByIdAndUser(UUID id, UserModel userModel) {
        return customerRepository.findByIdAndUser(id, userModel).orElse(null);
    }

    @Override
    public Page<CustomerModel> findAllByUser(Pageable pageable, UserModel userModel) {
        return customerRepository.findAllByUser(pageable, userModel);
    }

    @Override
    public CustomerModel findByCpfCnpjAndUser(String cpfCnpj, UserModel userModel) {
        return customerRepository.findByCpfCnpjAndUser(CpfOrCnpjValidator.cleanStringValue(cpfCnpj), userModel).orElse(null);
    }

    private void validateSave(SaveCustomerDto customerModel, UserModel userModel) {
        boolean isCustomerAlreadyRegistered = findByCpfCnpjAndUser(customerModel.getCpfCnpj(), userModel) != null;
        if (isCustomerAlreadyRegistered) throw new BusinessException("Você possui um cliente cadastrado com o CPF/CNPJ informado.");
    }

    private void validateUpdate(UpdateCustomerDto customerDto, UserModel userModel) {
        boolean isCustomerAlreadyRegistered = findByCpfCnpjAndUser(customerDto.getCpfCnpj(), userModel) != null;
        if (isCustomerAlreadyRegistered) throw new BusinessException("Você possui um cliente cadastrado com o CPF/CNPJ informado.");
    }
}
