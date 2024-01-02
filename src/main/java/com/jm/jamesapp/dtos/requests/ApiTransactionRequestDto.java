package com.jm.jamesapp.dtos.requests;

import com.jm.jamesapp.models.transaction.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

public record ApiTransactionRequestDto(
        @NotNull String customerId,
        @NotNull Instant paymentDate,
        String description,
        @NotNull @DecimalMin("0.0") BigDecimal amount,
        @NotNull TransactionType type

        ) {}
