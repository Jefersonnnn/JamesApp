package com.jm.jamesapp.dtos.requests;

import com.jm.jamesapp.models.GroupBillModel;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ApiGroupBillRequestDto(
        @NotBlank String name,
        @NotNull @DecimalMin("0.0") BigDecimal totalPayment,
        @NotNull GroupBillModel.BillingFrequency billingFrequency,
        String description
) {
}