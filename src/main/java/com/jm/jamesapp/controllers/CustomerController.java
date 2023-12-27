package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.ApiCustomerRequestDto;
import com.jm.jamesapp.dtos.requests.UpdateCustomerDto;
import com.jm.jamesapp.dtos.responses.CustomerResponseRecordDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.services.exceptions.ObjectNotFoundException;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    public ResponseEntity<Object> save(@RequestBody @Valid ApiCustomerRequestDto customerRequestRecordDto,
                                               Authentication authentication) {

        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var customerModel = new CustomerModel(userModel, customerRequestRecordDto.name(), customerRequestRecordDto.cpfCnpj());

        CustomerModel savedCustomer = customerService.save(customerModel, userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomerResponseRecordDto(savedCustomer));
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponseRecordDto>> getAllCustomers(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, Authentication authentication){

        // TODO: Analisar como deixar esse authentication global para não precisar receber em cada action
        UserModel ownerUser = (UserModel) authentication.getPrincipal();

        // TODO: Analisar para usar os dados do pageable de entrada para buscar somente o necessário
        List<CustomerModel> customersList = customerService.findAllByUser(ownerUser);

        ArrayList<CustomerResponseRecordDto> responseList = new ArrayList<>();

        for (var customer:customersList) {
            CustomerResponseRecordDto cResponse = new CustomerResponseRecordDto(customer);
            responseList.add(cResponse);
        }

        //Todo: Ver como funciona essa parada de Page
        Page<CustomerResponseRecordDto> pageResponse = new PageImpl<>(responseList, pageable, responseList.size());
        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(value="id") UUID id, Authentication authentication){
        UserModel ownerUser = (UserModel) authentication.getPrincipal();

        CustomerModel customer = customerService.findByIdAndUser(id, ownerUser);
        if (customer == null) throw new ObjectNotFoundException(id, "customer");

        return ResponseEntity.status(HttpStatus.OK).body(new CustomerResponseRecordDto(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid ApiCustomerRequestDto apiCustomerRequestDto,
                                                 Authentication authentication) {
        UserModel ownerUser = (UserModel) authentication.getPrincipal();

        CustomerModel customer = customerService.findByIdAndUser(id, ownerUser);
        if (customer == null) throw new ObjectNotFoundException(id, "customer");

        CustomerModel updatedCustomer = customerService.update(customer, new UpdateCustomerDto(apiCustomerRequestDto), ownerUser);

        return ResponseEntity.status(HttpStatus.OK).body(new CustomerResponseRecordDto(updatedCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value="id") UUID id, Authentication authentication){
        var ownerUser = (UserModel) authentication.getPrincipal();

        CustomerModel customer = customerService.findByIdAndUser(id, ownerUser);
        if (customer == null) throw new ObjectNotFoundException(id, "customer");

        customerService.delete(customer);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
