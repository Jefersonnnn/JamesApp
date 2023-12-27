package com.jm.jamesapp.services;

import com.jm.jamesapp.dtos.requests.UpdateCustomerDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.repositories.CustomerRepository;
import com.jm.jamesapp.services.exceptions.BusinessException;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.utils.constraints.CpfOrCnpjValidator;
import jakarta.transaction.Transactional;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.jm.jamesapp.utils.constraints.CpfOrCnpjValidator.cleanStringValue;

@Service
public class CustomerService implements ICustomerService {

    final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public CustomerModel save(CustomerModel customerModel, UserModel userModel) {
        customerModel.setCpfCnpj(cleanStringValue(customerModel.getCpfCnpj()));

        validateSave(customerModel, userModel);

        customerRepository.save(customerModel);
        return customerModel;
    }

    @Override
    @Transactional
    public CustomerModel update(CustomerModel customer, UpdateCustomerDto saveCustomerDto, UserModel ownerUser) { // TODO: REPLICAR DTO PARA SAVE
        validateUpdate(saveCustomerDto, ownerUser);

        customer.setName(saveCustomerDto.name);
        customer.setCpfCnpj(cleanStringValue(saveCustomerDto.cpfCnpj));
        customer.setUpdatedBy(ownerUser.getId());

        return customerRepository.save(customer);
    }

    @Override
    public CustomerModel findById(UUID id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(CustomerModel customerModel) {
        // TODO: transactionService.calculateCustomerBalance(customerModel) ... valida se tem saldo

        customerRepository.delete(customerModel);
    }

    @Override
    @Nullable
    public CustomerModel findByIdAndUser(UUID id, UserModel userModel) {
        return customerRepository.findByIdAndUser(id, userModel).orElse(null);
    }

    @Override
    public List<CustomerModel> findAllByUser(UserModel userModel) {
        return customerRepository.findAllByUser(userModel);
    }

    @Override
    @Nullable
    public CustomerModel findByCpfCnpjAndUser(String cpfCnpj, UserModel userModel) {
        return customerRepository.findByCpfCnpjAndUser(CpfOrCnpjValidator.cleanStringValue(cpfCnpj), userModel).orElse(null);
    }

    private void validateSave(CustomerModel customerModel, UserModel userModel) {
        boolean isCustomerAlreadyRegistered = findByCpfCnpjAndUser(customerModel.getCpfCnpj(), userModel) != null;
        if (isCustomerAlreadyRegistered) throw new BusinessException("Você possui um cliente cadastrado com o CPF/CNPJ informado.");
    }

    private void validateUpdate(UpdateCustomerDto customerDto, UserModel userModel) {
        boolean isCustomerAlreadyRegistered = findByCpfCnpjAndUser(customerDto.cpfCnpj, userModel) != null;
        if (isCustomerAlreadyRegistered) throw new BusinessException("Você possui um cliente cadastrado com o CPF/CNPJ informado.");
    }
}
