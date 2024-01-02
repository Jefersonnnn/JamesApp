package com.jm.jamesapp.models.dto;

import com.jm.jamesapp.dtos.requests.ApiTransactionRequestDto;
import com.jm.jamesapp.models.transaction.enums.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class SaveTransactionDto {

    private final Instant paymentDate;
    private final String description;
    private final BigDecimal amount;
    private final TransactionType type;

    public SaveTransactionDto(Instant paymentDate, String description, BigDecimal amount, TransactionType type) {
        this.paymentDate = paymentDate;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public SaveTransactionDto(ApiTransactionRequestDto requestDto) {
        this.paymentDate = requestDto.paymentDate();
        this.description = requestDto.description();
        this.amount = requestDto.amount();
        this.type = requestDto.type();
    }

    public Instant getPaymentDate() {
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
