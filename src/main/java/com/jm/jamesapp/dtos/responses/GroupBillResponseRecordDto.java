package com.jm.jamesapp.dtos.responses;

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
) {}
