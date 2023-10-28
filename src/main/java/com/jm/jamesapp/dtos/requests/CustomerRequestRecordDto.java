package com.jm.jamesapp.dtos.requests;

import com.jm.jamesapp.utils.constraints.ValidCpfOrCnpj;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record CustomerRequestRecordDto(
        @NotBlank String name,
        @NotNull @ValidCpfOrCnpj String cpfCnpj
) {}
