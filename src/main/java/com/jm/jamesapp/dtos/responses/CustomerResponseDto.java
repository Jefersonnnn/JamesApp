package com.jm.jamesapp.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jm.jamesapp.models.CustomerModel;

import java.math.BigDecimal;
import java.util.UUID;


public class CustomerResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID ownerId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cpfCnpj;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal balance;

    public CustomerResponseDto(UUID id, UUID ownerId, String name, String cpfCnpj, BigDecimal balance) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.cpfCnpj = cpfCnpj;
        this.balance = balance;
    }

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
    }

    public CustomerResponseDto(BigDecimal balance) {
        this.balance = balance;
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
