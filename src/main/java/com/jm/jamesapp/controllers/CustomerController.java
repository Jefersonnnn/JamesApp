package com.jm.jamesapp.controllers;

import com.jm.jamesapp.dtos.requests.ApiCustomerRequestDto;
import com.jm.jamesapp.models.dto.SaveCustomerDto;
import com.jm.jamesapp.models.dto.UpdateCustomerDto;
import com.jm.jamesapp.dtos.responses.CustomerResponseDto;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.UserModel;
import com.jm.jamesapp.security.IAuthenticationFacade;
import com.jm.jamesapp.security.exceptions.UnauthorizedException;
import com.jm.jamesapp.services.exceptions.ObjectNotFoundException;
import com.jm.jamesapp.services.interfaces.ICustomerService;
import com.jm.jamesapp.services.interfaces.IUserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/customers")
public class CustomerController {

    final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> save(@RequestBody @Valid ApiCustomerRequestDto apiCustomerRequestDto,
                                               Authentication authentication) {

        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        CustomerModel savedCustomer = customerService.save(new SaveCustomerDto(apiCustomerRequestDto), userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomerResponseDto(savedCustomer));
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponseDto>> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, Authentication authentication){
        UserModel userModel = (UserModel) authentication.getPrincipal();

        if (userModel == null) throw new UnauthorizedException();

        Page<CustomerModel> customersList = customerService.findAllByUser(pageable, userModel);

        Page<CustomerResponseDto> pageResponse = customersList.map(CustomerResponseDto::new);

        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> get(@PathVariable(value="id") UUID id, Authentication authentication){
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        CustomerModel customer = customerService.findByIdAndUser(id, userModel);
        if (customer == null) throw new ObjectNotFoundException(id, "customer");

        return ResponseEntity.status(HttpStatus.OK).body(new CustomerResponseDto(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> update(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid ApiCustomerRequestDto apiCustomerRequestDto,
                                                 Authentication authentication) {
        UserModel userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        CustomerModel customer = customerService.findByIdAndUser(id, userModel);
        if (customer == null) throw new ObjectNotFoundException(id, "customer");

        CustomerModel updatedCustomer = customerService.update(customer, new UpdateCustomerDto(apiCustomerRequestDto), userModel);

        return ResponseEntity.status(HttpStatus.OK).body(new CustomerResponseDto(updatedCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value="id") UUID id, Authentication authentication){
        var userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        CustomerModel customer = customerService.findByIdAndUser(id, userModel);
        if (customer == null) throw new ObjectNotFoundException(id, "customer");

        customerService.delete(customer);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> balance(@PathVariable(value="id") UUID id, Authentication authentication){
        var userModel = (UserModel) authentication.getPrincipal();
        if (userModel == null) throw new UnauthorizedException();

        BigDecimal balance = customerService.calculateBalance(id, userModel);
        if (balance == null) throw new ObjectNotFoundException(id, "customer");

        return ResponseEntity.ok(balance);
    }

}
