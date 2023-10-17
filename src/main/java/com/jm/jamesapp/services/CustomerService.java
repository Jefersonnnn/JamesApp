package com.jm.jamesapp.services;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.repositories.CustomerRepository;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService implements ICustomerService {

    final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public CustomerModel save(CustomerModel customerModel) {
        customerModel.setBalance(BigDecimal.valueOf(0.0));

        customerRepository.save(customerModel);
        return customerModel;
    }

    @Override
    public CustomerModel update(CustomerModel customerModel) {
        customerRepository.save(customerModel);
        return customerModel;
    }

    @Override
    public Page<CustomerModel> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Optional<CustomerModel> findById(UUID id) {
        return customerRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(CustomerModel customerModel) {

        // Regras para o Customers com balance > 0

        customerRepository.delete(customerModel);
    }
}
