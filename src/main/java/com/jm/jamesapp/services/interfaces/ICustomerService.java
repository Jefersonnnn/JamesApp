package com.jm.jamesapp.services.interfaces;

import com.jm.jamesapp.models.CustomerModel;

import java.util.Optional;

public interface ICustomerService extends IBaseService<CustomerModel> {

    Optional<CustomerModel> findByCpf(String cpfCnpj);
}
