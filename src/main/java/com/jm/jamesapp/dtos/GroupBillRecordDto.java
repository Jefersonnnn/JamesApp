package com.jm.jamesapp.dtos;

import com.jm.jamesapp.models.GroupBillModel;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record GroupBillRecordDto(
        @NotBlank String name,
        @NotBlank String ownerId,
        @NotNull @DecimalMin("0.0") BigDecimal totalPayment,
        @NotNull GroupBillModel.BillingFrequency billingFrequency,
        @NotNull @Min(0) @Max(31) Integer dueDateDay,
        @NotNull @Min(0) @Max(23) Integer dueDateHour,
        String description,

        List<UUID> customersIds
) {
}