package com.jm.jamesapp.dtos.responses;

import com.jm.jamesapp.models.billgroup.BillGroupModel;
import com.jm.jamesapp.models.billgroup.enums.BillingFrequency;

import java.math.BigDecimal;
import java.util.UUID;


public record BillGroupResponseDto(
        UUID id,
        UUID ownerId,
        String name,
        BigDecimal totalPayment,
        String description,
        BillingFrequency billingFrequency
) {
    public BillGroupResponseDto(BillGroupModel groupBillModel) {
        this(groupBillModel.getId(),
                groupBillModel.getUser().getId(),
                groupBillModel.getName(),
                groupBillModel.getValue(),
                groupBillModel.getDescription(),
                groupBillModel.getBillingFrequency());
    }
}
