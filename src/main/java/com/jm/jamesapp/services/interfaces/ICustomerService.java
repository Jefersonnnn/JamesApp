package com.jm.jamesapp.services.interfaces;

import com.jm.jamesapp.dtos.requests.UpdateCustomerDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.UserModel;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICustomerService {

    CustomerModel save(CustomerModel customerModel, UserModel userModel);

    CustomerModel update(CustomerModel customer, UpdateCustomerDto saveCustomerDto, UserModel ownerUser);

    CustomerModel findById(UUID id);

    void delete(CustomerModel customerModel);

    @Nullable
    CustomerModel findByIdAndUser(UUID id, UserModel userModel);

    @Nullable
    CustomerModel findByCpfCnpjAndUser(String cpfCnpj, UserModel userModel);

    List<CustomerModel> findAllByUser(UserModel userModel);
}
