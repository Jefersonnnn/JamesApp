package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.CustomerRequestRecordDto;
import com.jm.jamesapp.dtos.responses.CustomerResponseRecordDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> saveCustomer(@RequestBody @Valid CustomerRequestRecordDto customerRequestRecordDto, Authentication authentication) {

        var ownerUser = (UserModel) authentication.getPrincipal();

        if (ownerUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var customerModel = new CustomerModel();

        BeanUtils.copyProperties(customerRequestRecordDto, customerModel);

        customerModel.setOwner(ownerUser);
        customerService.save(customerModel);

        var customerResponse = new CustomerResponseRecordDto(customerModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponseRecordDto>> getAllCustomers(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, Authentication authentication){

        var ownerUser = (UserModel) authentication.getPrincipal();

        var customersList = customerService.findAllByOwner(ownerUser);

//        if(customersList.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

        var responseList = new ArrayList<CustomerResponseRecordDto>();

        for (var customer:customersList) {
            var cResponse = new CustomerResponseRecordDto(customer);
            responseList.add(cResponse);
        }

        //Todo: Ver como funciona essa parada de Page
        Page<CustomerResponseRecordDto> pageResponse = new PageImpl<>(responseList, pageable, responseList.size());
        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCustomer(@PathVariable(value="id") UUID id){
        Optional<CustomerModel> customerO = customerService.findById(id);

        if(customerO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }

        var customerResponse = new CustomerResponseRecordDto(customerO.get());

        return ResponseEntity.status(HttpStatus.OK).body(customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid CustomerRequestRecordDto customerRequestRecordDto) {
        Optional<CustomerModel> customerO = customerService.findById(id);

        if(customerO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }

        var customerModel = customerO.get();

        BeanUtils.copyProperties(customerRequestRecordDto, customerModel);

        customerService.update(customerModel);

        var customerResponse = new CustomerResponseRecordDto(customerModel);

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
