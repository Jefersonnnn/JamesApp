package com.jm.jamesapp.dtos.responses;

import com.jm.jamesapp.models.CustomerModel;

import java.math.BigDecimal;
import java.util.UUID;


public record CustomerResponseDto(
        UUID id,
        UUID ownerId,
        String name,
        String cpfCnpj
        //Todo: como vou retornar o balance para a resposta da chamada da API?
//        BigDecimal balance


) {
    public CustomerResponseDto(CustomerModel customerModel) {
        this(customerModel.getId(), customerModel.getUser().getId(), customerModel.getName(), customerModel.getCpfCnpj());
    }

}
