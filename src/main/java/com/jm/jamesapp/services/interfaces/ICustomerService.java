package com.jm.jamesapp.services.interfaces;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.models.dto.SaveCustomerDto;
import com.jm.jamesapp.models.dto.UpdateCustomerDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

public interface ICustomerService {

    @Transactional
    CustomerModel save(SaveCustomerDto saveCustomerDto, UserModel userModel);

    @Transactional
    CustomerModel update(CustomerModel customer, UpdateCustomerDto updateCustomerDto, UserModel userModel);

    @Nullable
    CustomerModel findById(UUID id);

    @Transactional
    void delete(CustomerModel customer);

    @Transactional
    void deleteWithPendingBalance(CustomerModel customer);

    @Nullable
    CustomerModel findByIdAndUser(UUID id, UserModel userModel);

    Page<CustomerModel> findAllByUser(Pageable pageable, UserModel userModel);

    @Nullable
    CustomerModel findByCpfCnpjAndUser(String cpfCnpj, UserModel userModel);

    BigDecimal calculateBalance(CustomerModel customerModel);
}
