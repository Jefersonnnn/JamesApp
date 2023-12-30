package com.jm.jamesapp.dtos.requests;

import com.jm.jamesapp.models.transaction.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record ApiTransactionRequestDto(
        UUID customerId,
        Date paymentDate,
        String description,
        BigDecimal amount,
        TransactionType type

        ) {}
