package com.jm.jamesapp.dtos.requests;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;


public record ApiTransactionRequestDto(
        Date dueDate,
        String description,
        @NotNull String customerCpfCnpj,
        BigDecimal amount

        ) {}
