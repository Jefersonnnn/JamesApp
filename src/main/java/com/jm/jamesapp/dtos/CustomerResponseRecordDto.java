package com.jm.jamesapp.dtos;

import com.jm.jamesapp.utils.constraints.ValidCpfOrCnpj;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;


public record CustomerResponseRecordDto(
        @NotNull UUID id,
        UUID owner,
        @NotBlank String name,
        @NotNull @ValidCpfOrCnpj String cpfCnpj,
        BigDecimal balance
) {}
