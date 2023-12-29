package com.jm.jamesapp.models.dto;

import com.jm.jamesapp.dtos.requests.ApiGroupBillRequestDto;
import com.jm.jamesapp.models.GroupBillModel;

import java.math.BigDecimal;

public class SaveGroupBillDto {
    private String name;
    private BigDecimal totalPayment;
    private GroupBillModel.BillingFrequency billingFrequency;
    private String description;

    public SaveGroupBillDto(ApiGroupBillRequestDto requestDto) {
        this.name = requestDto.name();
        this.totalPayment = requestDto.totalPayment();
        this.billingFrequency = requestDto.billingFrequency();
        this.description = requestDto.description();
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

    public GroupBillModel.BillingFrequency getBillingFrequency() {
        return billingFrequency;
    }

    public void setBillingFrequency(GroupBillModel.BillingFrequency billingFrequency) {
        this.billingFrequency = billingFrequency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
