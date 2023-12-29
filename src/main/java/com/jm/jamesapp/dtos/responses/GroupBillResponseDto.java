package com.jm.jamesapp.dtos.responses;

import com.jm.jamesapp.models.GroupBillModel;

import java.math.BigDecimal;
import java.util.UUID;


public record GroupBillResponseDto(
        UUID id,
        UUID ownerId,
        String name,
        BigDecimal totalPayment,
        String description,
        GroupBillModel.BillingFrequency billingFrequency
) {
    public GroupBillResponseDto(GroupBillModel groupBillModel) {
        this(groupBillModel.getId(),
                groupBillModel.getUser().getId(),
                groupBillModel.getName(),
                groupBillModel.getTotalPayment(),
                groupBillModel.getDescription(),
                groupBillModel.getBillingFrequency());
    }
}
