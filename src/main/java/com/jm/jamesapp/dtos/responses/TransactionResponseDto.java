package com.jm.jamesapp.dtos.responses;

import com.jm.jamesapp.models.transaction.TransactionModel;
import com.jm.jamesapp.models.transaction.enums.TransactionOrigin;
import com.jm.jamesapp.models.transaction.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


public record TransactionResponseDto(
        UUID id,
        TransactionOrigin origin,
        Date dueDate,
        TransactionType type,
        UUID ownerId,
        UUID customerId,
        BigDecimal amount,
        String description
        ) {
        public TransactionResponseDto(TransactionModel transaction) {
                this(
                        transaction.getId(),
                        transaction.getOrigin(),
                        transaction.getPaymentDate(),
                        transaction.getType(),
                        transaction.getUser().getId(),
                        transaction.getCustomer().getId(),
                        transaction.getAmount(),
                        transaction.getDescription()
                );
        }
}
