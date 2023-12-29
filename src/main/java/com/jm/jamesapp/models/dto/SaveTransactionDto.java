package com.jm.jamesapp.models.dto;

import com.jm.jamesapp.dtos.requests.ApiTransactionRequestDto;
import com.jm.jamesapp.models.CustomerModel;

import java.math.BigDecimal;
import java.util.Date;

public class SaveTransactionDto {

    private final CustomerModel sender;
    private final Date dueDate;
    private final String description;
    private final String customerCpfCnpj;
    private final BigDecimal amount;

    public SaveTransactionDto(CustomerModel sender, Date dueDate, String description, String customerCpfCnpj, BigDecimal amount) {
        this.sender = sender;
        this.dueDate = dueDate;
        this.description = description;
        this.customerCpfCnpj = customerCpfCnpj;
        this.amount = amount;
    }

    public SaveTransactionDto(CustomerModel sender, ApiTransactionRequestDto requestDto) {
        this.dueDate = requestDto.dueDate();
        this.description = requestDto.description();
        this.customerCpfCnpj = requestDto.customerCpfCnpj();
        this.amount = requestDto.amount();
        this.sender = sender;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public String getCustomerCpfCnpj() {
        return customerCpfCnpj;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CustomerModel getSender() {
        return sender;
    }

}
