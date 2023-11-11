package com.jm.jamesapp.services.interfaces;

import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICustomerService extends IBaseService<CustomerModel> {

    Optional<CustomerModel> findByIdAndOwner(UUID id, UserModel userModel);

    Optional<CustomerModel> findByCpfCnpj(String cpfCnpj);

    Optional<CustomerModel> findByCpfCnpjAndOwner(String cpfCnpj, UserModel userModel);

    List<CustomerModel> findAllByOwner(UserModel userModel);
}
