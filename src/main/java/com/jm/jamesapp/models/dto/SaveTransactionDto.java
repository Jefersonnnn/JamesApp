package com.jm.jamesapp.models.dto;

import com.jm.jamesapp.dtos.requests.ApiTransactionRequestDto;
import com.jm.jamesapp.models.transaction.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

public class SaveTransactionDto {

    private final Date paymentDate;
    private final String description;
    private final BigDecimal amount;
    private final TransactionType type;

    public SaveTransactionDto(ApiTransactionRequestDto requestDto) {
        this.paymentDate = requestDto.paymentDate();
        this.description = requestDto.description();
        this.amount = requestDto.amount();
        this.type = requestDto.type();
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }
}
