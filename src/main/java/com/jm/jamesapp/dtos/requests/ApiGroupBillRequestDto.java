package com.jm.jamesapp.dtos.requests;

import com.jm.jamesapp.models.billgroup.enums.BillingFrequency;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ApiGroupBillRequestDto(
        @NotBlank String name,
        @NotNull @DecimalMin("0.0") BigDecimal totalPayment,
        @NotNull BillingFrequency billingFrequency,
        @NotNull @Min(1) @Max(31) Integer maxClosureDay,
        String description
) {
}