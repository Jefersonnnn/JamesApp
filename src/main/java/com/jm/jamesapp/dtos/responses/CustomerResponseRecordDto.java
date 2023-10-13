package com.jm.jamesapp.dtos.responses;

import java.math.BigDecimal;
import java.util.UUID;


public record CustomerResponseRecordDto(
        UUID id,
        UUID ownerId,
        String name,
        String cpfCnpj,
        BigDecimal balance
) {}
