package com.jm.jamesapp.dtos;

import com.jm.jamesapp.utils.constraints.ValidCpfOrCnpj;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record CustomerRecordDto(
        @NotBlank String ownerId,
        @NotBlank String name,
        @NotNull @ValidCpfOrCnpj String cpfCnpj
) {}
