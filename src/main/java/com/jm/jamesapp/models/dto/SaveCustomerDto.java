package com.jm.jamesapp.models.dto;

import com.jm.jamesapp.dtos.requests.ApiCustomerRequestDto;

public class SaveCustomerDto {
    private String name;
    private String cpfCnpj;

    public SaveCustomerDto() {
    }

    public SaveCustomerDto(ApiCustomerRequestDto requestRecordDto) {
        this.name = requestRecordDto.name();
        this.cpfCnpj = requestRecordDto.cpfCnpj();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }
}
