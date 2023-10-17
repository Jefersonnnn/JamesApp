package com.jm.jamesapp.dtos.responses;

import com.jm.jamesapp.models.CustomerModel;

import java.math.BigDecimal;
import java.util.UUID;


public record CustomerResponseRecordDto(
        UUID id,
        UUID ownerId,
        String name,
        String cpfCnpj,
        BigDecimal balance


) {
    public CustomerResponseRecordDto(CustomerModel customerModel) {
        this(customerModel.getId(), customerModel.getOwner().getId(), customerModel.getName(), customerModel.getCpfCnpj(), customerModel.getBalance());
    }

}
