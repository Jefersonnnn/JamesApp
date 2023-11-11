package com.jm.jamesapp.dtos.responses;

import com.jm.jamesapp.models.TransactionModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


public record TransactionResponseRecordDto(
        UUID id,
        boolean automatic,
        Date dueDate,
        TransactionModel.TypeTransaction type,
        UUID ownerId,
        UUID customerId,
        BigDecimal amount,
        String description,
        TransactionModel.StatusTransaction status,
        String cancelDescription
        ) {
        public TransactionResponseRecordDto (TransactionModel transaction) {
                this(
                        transaction.getId(),
                        transaction.isAutomatic(),
                        transaction.getDueDate(),
                        transaction.getTypeTransaction(),
                        transaction.getOwner().getId(),
                        transaction.getCustomer().getId(),
                        transaction.getAmount(),
                        transaction.getDescription(),
                        transaction.getStatus(),
                        transaction.getCancelDescription()
                );
        }
}
