package com.jm.jamesapp.dtos.responses;

import com.jm.jamesapp.models.GroupBillModel;

import java.math.BigDecimal;
import java.util.UUID;


public record GroupBillResponseRecordDto(
        UUID id,
        UUID ownerId,
        String name,
        BigDecimal totalPayment,
        Integer dueDateDay,
        Integer dueDateHour,
        String description
) {
    public GroupBillResponseRecordDto(GroupBillModel groupBillModel) {
        this(groupBillModel.getId(),
                groupBillModel.getUser().getId(),
                groupBillModel.getName(),
                groupBillModel.getTotalPayment(),
                groupBillModel.getDueDateDay(),
                groupBillModel.getDueDateHour(),
                groupBillModel.getDescription());
    }
}
