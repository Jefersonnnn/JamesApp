package com.jm.jamesapp.dtos.requests;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;


public record TransactionRequestRecordDto(
        Date dueDate,
        String description,
        @NotNull String ownerId,
        @NotNull String customerCpfCnpj,
        BigDecimal amount

        ) {}
