package com.jm.jamesapp.models.dto;

import com.jm.jamesapp.dtos.requests.ApiCustomerRequestDto;
import com.jm.jamesapp.dtos.requests.ApiTransactionRequestDto;

import java.math.BigDecimal;
import java.util.Date;

public class UpdateTransactionDto {
    private Date dueDate;
    private String description;
    private String customerCpfCnpj;
    private BigDecimal amount;

    public UpdateTransactionDto(ApiTransactionRequestDto requestDto) {
        this.dueDate = requestDto.dueDate();
        this.description = requestDto.description();
        this.customerCpfCnpj = requestDto.customerCpfCnpj() ;
        this.amount = requestDto.amount();
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomerCpfCnpj() {
        return customerCpfCnpj;
    }

    public void setCustomerCpfCnpj(String customerCpfCnpj) {
        this.customerCpfCnpj = customerCpfCnpj;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
