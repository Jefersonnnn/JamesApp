package com.jm.jamesapp.dtos.responses;

import com.jm.jamesapp.models.CustomerModel;

import java.math.BigDecimal;
import java.util.UUID;

public class CustomerResponseDto {
    private UUID id;
    private UUID ownerId;
    private String name;
    private String cpfCnpj;
    private BigDecimal balance;

    public CustomerResponseDto(CustomerModel customerModel, BigDecimal balance) {
        this.id = customerModel.getId();
        this.ownerId = customerModel.getUser().getId();
        this.name = customerModel.getName();
        this.cpfCnpj = customerModel.getCpfCnpj();
        this.balance = balance;
    }

    public CustomerResponseDto(CustomerModel customerModel) {
        this.id = customerModel.getId();
        this.ownerId = customerModel.getUser().getId();
        this.name = customerModel.getName();
        this.cpfCnpj = customerModel.getCpfCnpj();
        this.balance = BigDecimal.ZERO;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public BigDecimal getBalance() {
        return balance;
    }

}
