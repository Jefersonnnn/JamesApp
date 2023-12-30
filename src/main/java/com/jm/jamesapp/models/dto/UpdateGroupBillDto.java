package com.jm.jamesapp.models.dto;

import com.jm.jamesapp.dtos.requests.ApiGroupBillRequestDto;
import com.jm.jamesapp.models.billgroup.BillGroupModel;
import com.jm.jamesapp.models.billgroup.enums.BillingFrequency;

import java.math.BigDecimal;
import java.util.UUID;

public class UpdateGroupBillDto {
    private UUID id;
    private String name;
    private BigDecimal totalPayment;
    private BillingFrequency billingFrequency;
    private String description;

    public UpdateGroupBillDto(ApiGroupBillRequestDto requestDto, UUID id) {
        this.id = id;
        this.name = requestDto.name();
        this.totalPayment = requestDto.totalPayment();
        this.billingFrequency = requestDto.billingFrequency();
        this.description = requestDto.description();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public BillingFrequency getBillingFrequency() {
        return billingFrequency;
    }

    public void setBillingFrequency(BillingFrequency billingFrequency) {
        this.billingFrequency = billingFrequency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
