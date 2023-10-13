package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.CustomerRecordDto;
import com.jm.jamesapp.dtos.CustomerResponseRecordDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/customers")
public class CustomerController {

    final ICustomerService customerService;
    final IUserService userService;

    public CustomerController(ICustomerService customerService, IUserService userService) {
        this.customerService = customerService;
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<Object> saveCustomer(@RequestBody @Valid CustomerRecordDto customerRecordDto) {
        var ownerUser = userService.findById(UUID.fromString(customerRecordDto.owner()));

        if(ownerUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Owner not found.");
        }

        var customerModel = new CustomerModel();
        customerModel.setOwner(ownerUser.get());
        customerModel.setBalance(BigDecimal.valueOf(0.0));

        BeanUtils.copyProperties(customerRecordDto, customerModel);

        customerService.save(customerModel);

        var customerResponse = new CustomerResponseRecordDto(
                customerModel.getId(),
                customerModel.getOwner().getId(),
                customerModel.getName(),
                customerModel.getCpfCnpj(),
                customerModel.getBalance());

        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }

    @GetMapping
    public ResponseEntity<Object> getAllCustomers(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        var customersList = customerService.findAll(pageable);
        if(customersList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customers not found.");
        }

        var responseList = new ArrayList<CustomerResponseRecordDto>();

        for (var customer:customersList) {
            var cResponse = new CustomerResponseRecordDto(
                    customer.getId(),
                    customer.getOwner().getId(),
                    customer.getName(),
                    customer.getCpfCnpj(),
                    customer.getBalance()
            );
            responseList.add(cResponse);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCustomer(@PathVariable(value="id") UUID id){
        Optional<CustomerModel> customerO = customerService.findById(id);
        if(customerO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }

        var customer = customerO.get();
        var customerResponse = new CustomerResponseRecordDto(
                customer.getId(),
                customer.getOwner().getId(),
                customer.getName(),
                customer.getCpfCnpj(),
                customer.getBalance());

        return ResponseEntity.status(HttpStatus.OK).body(customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid CustomerRecordDto customerRecordDto) {
        Optional<CustomerModel> customerO = customerService.findById(id);
        if(customerO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }
        var customerModel = customerO.get();
        BeanUtils.copyProperties(customerRecordDto, customerModel);

        customerService.save(customerModel);

        var customerResponse = new CustomerResponseRecordDto(
                customerModel.getId(),
                customerModel.getOwner().getId(),
                customerModel.getName(),
                customerModel.getCpfCnpj(),
                customerModel.getBalance());

        return ResponseEntity.status(HttpStatus.OK).body(customerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(value="id") UUID id){
        Optional<CustomerModel> customerO = customerService.findById(id);
        if(customerO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }
        customerService.delete(customerO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully.");
    }

}
