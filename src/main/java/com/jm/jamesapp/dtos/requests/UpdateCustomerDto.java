package com.jm.jamesapp.dtos.requests;

public class UpdateCustomerDto {
    public String name;
    public String cpfCnpj;

    public UpdateCustomerDto(ApiCustomerRequestDto requestRecordDto) {
        this.name = requestRecordDto.name();
        this.cpfCnpj = requestRecordDto.cpfCnpj();
    }
}
