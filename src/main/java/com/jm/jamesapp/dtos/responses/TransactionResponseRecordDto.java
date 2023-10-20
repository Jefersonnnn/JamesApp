package com.jm.jamesapp.dtos.responses;

import com.jm.jamesapp.models.TransactionModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


public record TransactionResponseRecordDto(
        UUID id,
        Date dueDate,
        TransactionModel.TypeTransaction type,
        UUID ownerId,
        UUID customerId,
        BigDecimal amount,
        String description,
        String cancelDescription
        ) {
        public TransactionResponseRecordDto (TransactionModel transaction) {
                this(
                        transaction.getId(),
                        transaction.getDueDate(),
                        transaction.getType(),
                        transaction.getOwner().getId(),
                        transaction.getCustomer().getId(),
                        transaction.getAmount(),
                        transaction.getDescription(),
                        transaction.getCancelDescription()
                );
        }
}
